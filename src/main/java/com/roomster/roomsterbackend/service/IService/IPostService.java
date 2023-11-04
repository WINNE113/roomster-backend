package com.roomster.roomsterbackend.service.IService;

import com.roomster.roomsterbackend.dto.PostDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IPostService {
    List<PostDto> getAllPost(Pageable pageable);
    List<PostDto> getPostByAuthorId(Pageable pageable, Long authorId);

    PostDto getPostById(Long postId);
}
