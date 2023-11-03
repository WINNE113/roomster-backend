package com.roomster.roomsterbackend.controller.client;

import com.roomster.roomsterbackend.entity.CommentEntity;
import com.roomster.roomsterbackend.entity.PostEntity;
import com.roomster.roomsterbackend.entity.UserEntity;
import com.roomster.roomsterbackend.repository.PostRepository;
import com.roomster.roomsterbackend.repository.UserRepository;
import com.roomster.roomsterbackend.service.impl.CommentService;
import com.roomster.roomsterbackend.service.impl.PostService;
import com.roomster.roomsterbackend.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
public class CommentController {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentService commentService;

    @GetMapping("/comment/{id}")
    public String showComment(@PathVariable Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String phoneNumber = authentication.getName();
        Optional<PostEntity> postEntity = postRepository.findById(id);
        if(postEntity.isPresent()){
            Optional<UserEntity> userEntity = userRepository.findByPhoneNumber(phoneNumber);
            if (userEntity.isPresent()){
                CommentEntity comment = new CommentEntity();
                comment.setPostComment(postEntity.get());
                comment.setUserComment(userEntity.get());

                return "Success";
            } else {
                return "error";
            }
        }else {
            return "error";
        }
    }

    @PostMapping("/create-comment")
    public String createNewPost(@PathVariable CommentEntity comment) {
           commentService.save(comment);
           return "Comment success";
    }
}
