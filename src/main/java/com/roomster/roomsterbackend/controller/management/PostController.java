package com.roomster.roomsterbackend.controller.management;

import com.roomster.roomsterbackend.dto.PostDto;
import com.roomster.roomsterbackend.entity.PostEntity;
import com.roomster.roomsterbackend.service.impl.PostService;
import com.roomster.roomsterbackend.service.impl.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/auth")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;

    @PostMapping("/create-post")
    public void  createPost(@RequestBody PostDto postDto){
        postService.createPost(postDto);
    }
//    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){
//        PostEntity postRequest = modelMapper.map(postDto, PostEntity.class);
//
//        PostEntity post = postService.createPost(postRequest);
//
//        // convert entity to DTO
//        PostDto postResponse = modelMapper.map(post, PostDto.class);
//         return new ResponseEntity<PostDto>(postResponse, HttpStatus.CREATED);
//    }


    @GetMapping("/list-post")
    public List<PostDto> ListPost(){
        return  postService.getAllPost().stream().map(postEntity -> modelMapper.map(postEntity, PostDto.class))
                .collect(Collectors.toList());
    }
}
