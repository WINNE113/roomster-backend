package com.roomster.roomsterbackend.service.impl;

import com.roomster.roomsterbackend.dto.CommentPostDto;

import java.util.List;

public interface ICommentPostService {
    CommentPostDto saveNewComment(CommentPostDto commentPostDTO);
    CommentPostDto updateComment(CommentPostDto commentPostDTO);
    void deleteComment(CommentPostDto commentPostDTO);
    List<CommentPostDto> getAllCommentOfPost(Long postId);
}
