package com.roomster.roomsterbackend.controller.management;

import com.roomster.roomsterbackend.dto.BaseResponse;
import com.roomster.roomsterbackend.dto.comment.CommentPostDto;
import com.roomster.roomsterbackend.service.IService.ICommentPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CommentController {
    private final ICommentPostService service;
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_MANAGE')")
    @PostMapping("/new")
    public BaseResponse saveNewCommentPost(@RequestBody CommentPostDto commentPostDTO, Principal connectedUser) {
        try {
            service.saveNewComment(commentPostDTO, connectedUser);
        }catch (Exception ex){
            BaseResponse.error(ex.getMessage());
        }
        return BaseResponse.success("Thêm Bình Luận Thành Công");
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/update")
    public BaseResponse updateCommentPost(@RequestParam Long commentId, @RequestBody CommentPostDto commentPostDTO) {
        try {
            service.updateComment(commentId,commentPostDTO);
        }catch (Exception ex){
            return BaseResponse.error(ex.getMessage());
        }
        return BaseResponse.success("Cập nhật bình luận thành công!");
    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @DeleteMapping("/delete")
    public BaseResponse deleteCommentPost(@RequestParam Long commentId) {
        return service.deleteComment(commentId);
    }

    @GetMapping("/list/{postId}")
    public List<CommentPostDto> getAllCommentOfPost(@PathVariable(name = "postId") Long postId) {
        return service.getAllCommentOfPost(postId);
    }
}
