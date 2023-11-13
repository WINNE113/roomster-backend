package com.roomster.roomsterbackend.controller.management;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roomster.roomsterbackend.dto.PostDto;
import com.roomster.roomsterbackend.service.IService.IDatabaseSearch;
import com.roomster.roomsterbackend.service.IService.IPostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/post")
@PreAuthorize("hasAnyRole('ROLE_MANAGE','ROLE_ADMIN')")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PostController {

    private final IPostService service;

    private final IDatabaseSearch iDatabaseSearch;
    @GetMapping("/list")
    public List<PostDto> listPost(@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                  @RequestParam(name = "size", required = false, defaultValue = "5") Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return service.getAllPost(pageable);
    }

    @PostMapping(value = "/new", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public HttpStatus saveNewPost(@RequestBody PostDto postDTO,
                                  @RequestPart(required = false, name = "images") @Valid List<MultipartFile> images) throws IOException {
        service.saveNewPost(postDTO, images);
        return HttpStatus.OK;
    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_MANAGE','ROLE_ADMIN')")
    @PostMapping(value = "/filters",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public List<PostDto> searchPost(@RequestPart String json) throws SQLException {
        ObjectMapper objectMapper = new ObjectMapper();
         LinkedHashMap<String, Object> map = null;
        try {
            map = objectMapper.readValue(json, LinkedHashMap.class);
            convertStringToArray(map);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return iDatabaseSearch.searchFilter(map);
    }

    private static void convertStringToArray(LinkedHashMap<String, Object> map) {
        for(String key : map.keySet()){
            if(key.equals("price")){
                String priceRange = (String) map.get(key);
                int[] price = Arrays.stream(priceRange.split(",")).mapToInt(Integer::parseInt).toArray();
                map.put(key, price);
            } else if (key.equals("acreage")) {
                String acreageRange = (String) map.get(key);
                int[] acreage = Arrays.stream(acreageRange.split(",")).mapToInt(Integer::parseInt).toArray();
                map.put(key,acreage);
            }
        }
    }
}
