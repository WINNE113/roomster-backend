package com.roomster.roomsterbackend.service.IService;

import com.roomster.roomsterbackend.dto.BaseResponse;
import com.roomster.roomsterbackend.dto.comment.CommentPostDto;

import java.security.Principal;
import java.util.List;

public interface ICommentPostService {
    CommentPostDto saveNewComment(CommentPostDto commentPostDTO, Principal connectedUser);
    CommentPostDto updateComment(Long commentId, CommentPostDto commentPostDTO, Principal connectedUser);
    BaseResponse deleteComment(Long commentId);
    List<CommentPostDto> getAllCommentOfPost(Long postId);
}
