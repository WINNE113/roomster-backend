package com.roomster.roomsterbackend.service.impl;

import com.roomster.roomsterbackend.dto.PostDto;
import com.roomster.roomsterbackend.entity.PostEntity;
import com.roomster.roomsterbackend.entity.UserEntity;
import com.roomster.roomsterbackend.repository.PostRepository;
import com.roomster.roomsterbackend.repository.UserRepository;
import com.roomster.roomsterbackend.service.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PostService implements IPostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    private Date date = new Date();
    /*@Override
    public PostEntity createPost(PostEntity postEntity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String phoneNumber = authentication.getName();
        Optional<UserEntity> userEntity = userRepository.findByPhoneNumber(phoneNumber);
        postEntity.setCreatedDate(date);
        postEntity.setActive(false);
        postEntity.setCreatedBy(userEntity.get().getUserName());
        postEntity.setUser(userEntity.get());
        return postRepository.save(postEntity);
    }*/

    @Override
    public void createPost(PostDto postDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String phoneNumber = authentication.getName();
        Optional<UserEntity> userEntity = userRepository.findByPhoneNumber(phoneNumber);
        PostEntity post = new PostEntity();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setAddress(postDto.getAddress());
        post.getPostType().setName(postDto.getPostType());
        post.setCreatedDate(date);
        post.setCreatedBy(userEntity.get().getUserName());
        post.setActive(false);
    }

    @Override
    public List<PostEntity> getAllPost() {
        return postRepository.findAll();
    }
}
