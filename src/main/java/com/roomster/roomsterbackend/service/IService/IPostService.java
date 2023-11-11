package com.roomster.roomsterbackend.service.IService;

import com.roomster.roomsterbackend.dto.PostDto;
import com.roomster.roomsterbackend.entity.PostEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IPostService {
    List<PostDto> getAllPostByType();
    List<PostDto> getAllPost(Pageable pageable);
    List<PostDto> getAllPost();
    List<PostDto> getPostByAuthorId(Pageable pageable, Long authorId);

    PostDto getPostById(Long postId);
    void saveNewPost(PostDto postDTO,
                     List<MultipartFile> images) throws IOException;

    PostDto updatePost(PostDto postDto);
    void deletePost(Long postId);

}
