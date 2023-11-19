package com.roomster.roomsterbackend.controller.guest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roomster.roomsterbackend.dto.*;
import com.roomster.roomsterbackend.service.IService.IDatabaseSearch;
import com.roomster.roomsterbackend.service.IService.IPostService;
import com.roomster.roomsterbackend.service.impl.ProvinceService;
import com.roomster.roomsterbackend.util.ConvertUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/guest")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class GuestController {

    private final IPostService postService;

    private final IDatabaseSearch iDatabaseSearch;

    private final ProvinceService provinceService;
    @GetMapping(value = "/list-post-by-rating")
    public List<PostDtoWithRating> ListPostByRating(@RequestParam( name = "page", required = false, defaultValue = "0") Integer page,
                                                    @RequestParam(name = "size", required = false, defaultValue = "5") Integer size){
        Pageable pageable = PageRequest.of(page, size);

        return postService.getPostByRating(pageable);
    }
    @PostMapping(value = "/post/filters", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public SearchResult searchPost(@RequestPart(required = false) String json,
                                                       @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                                       @RequestParam(name = "size", required = false, defaultValue = "5") Integer size) throws SQLException {

        Pageable pageable = PageRequest.of(page, size);
        ObjectMapper objectMapper = new ObjectMapper();
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        if(json != null) {
            try {
                map = objectMapper.readValue(json, LinkedHashMap.class);
                ConvertUtil.convertStringToArray(map);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        return iDatabaseSearch.searchFilter(pageable, map);
    }

    @GetMapping(value = "/post/top-province")
    public List<ProvinceDtoWithImage> getTopOfProvince(@RequestParam(name = "page" , required = false, defaultValue = "5") Integer page,
                                              @RequestParam(name = "size" , required = false, defaultValue = "5") Integer size){
        Pageable pageable = PageRequest.of(page, size);
        List<ProvinceDto> topOfProvince = postService.getTopOfProvince(pageable);

        return provinceService.setImages(topOfProvince);
    }

    @GetMapping(value = "/postDetail")
    public Optional<PostDetailDto> getPostDetail(@RequestParam Long postId){
        return postService.getPostDetail(postId);
    }

    @GetMapping(value = "/post/images")
    public List<PostImageDto> getPostImage(@RequestParam Long postId){
        return postService.getPostImages(postId);
    }
}
