package com.roomster.roomsterbackend.controller.management;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roomster.roomsterbackend.dto.PostDto;
import com.roomster.roomsterbackend.entity.PostEntity;
import com.roomster.roomsterbackend.service.IService.IDatabaseSearch;
import com.roomster.roomsterbackend.service.IService.IPostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1/post")
@PreAuthorize("hasAnyRole('ROLE_MANAGE','ROLE_ADMIN')")
@RequiredArgsConstructor
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
    public HttpStatus saveNewPost(@RequestPart String postDto, @RequestPart(required = false, name = "images") @Valid List<MultipartFile> images) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        PostDto postDTO = objectMapper.readValue(postDto, PostDto.class);
        service.saveNewPost(postDTO, images);
        return HttpStatus.OK;
    }

    @PostMapping(value = "/filters",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public List<PostDto> searchPost(@RequestPart String json) throws SQLException {
        ObjectMapper objectMapper = new ObjectMapper();
        LinkedHashMap<String, Object> map = null;
        try {
            map = objectMapper.readValue(json, LinkedHashMap.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return iDatabaseSearch.searchFilter(map);
    }
    @GetMapping("/listAllPost")
    public List<PostDto> getAllPost(Pageable pageable){
        return service.getAllPost(pageable);
    }

    @GetMapping("/listAll")
    public List<PostEntity> getAll(){
        return service.getAllPost();
    }


    @GetMapping("/list/{postId}")
    public PostDto getPostById(@PathVariable(name = "postId") Long postId) {
        return service.getPostById(postId);
    }

    @GetMapping("/danhsach/{postType}")
    public List<PostDto> getPostByType(@PathVariable(name = "postType") String postType, Pageable pageable){
        return  service.getAllPostBy(pageable, postType);
    }
}
