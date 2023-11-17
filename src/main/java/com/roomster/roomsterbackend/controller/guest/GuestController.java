package com.roomster.roomsterbackend.controller.guest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roomster.roomsterbackend.dto.PostDtoWithRating;
import com.roomster.roomsterbackend.dto.SearchResult;
import com.roomster.roomsterbackend.service.IService.IDatabaseSearch;
import com.roomster.roomsterbackend.service.IService.IPostService;
import com.roomster.roomsterbackend.util.ConvertUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1/guest")
@RequiredArgsConstructor
public class GuestController {

    private final IPostService postService;

    private final IDatabaseSearch iDatabaseSearch;
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
}
