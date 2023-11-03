package com.roomster.roomsterbackend.service;

import com.roomster.roomsterbackend.dto.PostDto;
import com.roomster.roomsterbackend.entity.PostEntity;

import java.util.List;

public interface IPostService {
//    public PostEntity createPost(PostEntity postEntity);

    public void createPost(PostDto postDto);

    public List<PostEntity> getAllPost();
}
