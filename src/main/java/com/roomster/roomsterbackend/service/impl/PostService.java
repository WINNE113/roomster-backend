package com.roomster.roomsterbackend.service.impl;

import com.roomster.roomsterbackend.dto.PostDto;
import com.roomster.roomsterbackend.mapper.PostMapper;
import com.roomster.roomsterbackend.repository.PostRepository;
import com.roomster.roomsterbackend.service.IService.IPostService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService implements IPostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostMapper postMapper;
    @Override
    public List<PostDto> getAllPost(Pageable pageable) {
        return postRepository.findAll(pageable)
                .stream()
                .map(postEntity -> postMapper.entityToDto(postEntity))
                .filter(postDto -> !postDto.isDeleted())
                .collect(Collectors.toList());
    }

    @Override
    public List<PostDto> getPostByAuthorId(Pageable pageable, Long authorId) {
        return postRepository.getPostEntityByAuthorId(pageable, authorId)
                .stream()
                .map(postEntity -> postMapper.entityToDto(postEntity))
                .filter(postDto -> !postDto.isDeleted())
                .collect(Collectors.toList());
    }

    @Override
    public PostDto getPostById(Long postId) {
        return postMapper.entityToDto(postRepository.findById(postId).orElseThrow(EntityNotFoundException::new));
    }
}
