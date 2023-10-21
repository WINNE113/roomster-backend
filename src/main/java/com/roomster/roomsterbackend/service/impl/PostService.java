package com.roomster.roomsterbackend.service.impl;

import com.roomster.roomsterbackend.dto.PostDto;
import com.roomster.roomsterbackend.repository.PostRepository;
import com.roomster.roomsterbackend.service.IService.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService implements IPostService {
    @Autowired
    private PostRepository postRepository;

    @Override
    public List<PostDto> getAllPost(Pageable pageable) {
        return null;
    }
}
