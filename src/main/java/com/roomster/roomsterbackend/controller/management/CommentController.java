package com.roomster.roomsterbackend.controller.management;

import com.roomster.roomsterbackend.dto.CommentPostDto;
import com.roomster.roomsterbackend.service.impl.ICommentPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth/comment")
@RequiredArgsConstructor
public class CommentController {
    private final ICommentPostService service;
    @PostMapping("/new")
    public CommentPostDto saveNewCommentPost(@RequestBody CommentPostDto commentPostDTO) {
        return service.saveNewComment(commentPostDTO);
    }

    @PutMapping("/update")
    public CommentPostDto updateCommentPost(@RequestBody CommentPostDto commentPostDTO) {
        return service.updateComment(commentPostDTO);
    }

    @DeleteMapping("/delete")
    public void deleteCommentPost(@RequestBody CommentPostDto commentPostDTO) {
        service.deleteComment(commentPostDTO);
    }

    @GetMapping("/list/{postId}")
    public List<CommentPostDto> getAllCommentOfPost(@PathVariable(name = "postId") Long postId) {
        return service.getAllCommentOfPost(postId);
    }
}
