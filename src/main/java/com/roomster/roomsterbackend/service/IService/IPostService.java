package com.roomster.roomsterbackend.service.IService;


import com.roomster.roomsterbackend.dto.post.*;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

public interface IPostService {
    List<PostDto> getAllPost(Pageable pageable);
    List<PostDto> getPostByAuthorId(Pageable pageable, Long authorId);

    PostDto getPostById(Long postId);
    void upsertPost(PostDto postDTO,
                    List<MultipartFile> images, Principal connectedUser) throws IOException;

    List<PostDtoWithRating> getPostByRating(Pageable pageable);

    List<ProvinceDto> getTopOfProvince(Pageable pageable);

    PostDto updatePost(PostDto postDto);

    void deletePost(Long postId);

    PostDetailDtoImp getPostDetail(Long postId);

    List<PostImageDto> getPostImages(Long postId);

    void deletePostById(Long postId);
}
