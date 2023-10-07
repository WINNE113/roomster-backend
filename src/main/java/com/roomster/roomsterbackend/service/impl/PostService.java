package com.roomster.roomsterbackend.service.impl;

import com.roomster.roomsterbackend.repository.PostRepository;
import com.roomster.roomsterbackend.service.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService implements IPostService {
    @Autowired
    private PostRepository postRepository;

}
