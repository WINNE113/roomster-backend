package com.roomster.roomsterbackend.service.impl;

import com.cloudinary.Cloudinary;
import com.roomster.roomsterbackend.dto.PostDto;
import com.roomster.roomsterbackend.entity.PostEntity;
import com.roomster.roomsterbackend.mapper.PostMapper;
import com.roomster.roomsterbackend.repository.PostRepository;
import com.roomster.roomsterbackend.service.IService.IPostService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService implements IPostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private InforRoomMapper inforRoomMapper;


    @Autowired
    private PostTypeRepository postTypeRepository;


    private final Cloudinary cloudinary;
    @Override
    public List<PostDto> getAllPostByType() {
        return postRepository.findAll()
                .stream()
                .map(postEntity -> postMapper.entityToDto(postEntity))
                .filter(postDto -> !postDto.isDeleted())
                .toList();
    }

    @Override
    public List<PostDto> getAllPost(Pageable pageable) {
        Page<PostEntity> postPage = postRepository.findAll(pageable);

        // Get the content (posts) from the page
        List<PostEntity> posts = postPage.getContent();

        List<PostDto> postDtos = posts.stream()
                .map(postEntity -> postMapper.entityToDto(postEntity))
                .filter(postDto -> !postDto.isDeleted())
                .toList();
        return postDtos;

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

    @Override
    public void saveNewPost(PostDto postDTO, List<MultipartFile> images, Principal connectedUser) throws IOException {
        PostEntity postEntity = postMapper.dtoToEntity(postDTO);
        postEntity.setPostType(postTypeRepository.getPostEntityByCode(postDTO.getPost_type()));
        postEntity.setDeleted(false);
        if(postDTO.getRotation() != null){
            postEntity.setRotation(postDTO.getRotation());
        }
        var user = (UserEntity)((UsernamePasswordAuthenticationToken)connectedUser).getPrincipal();
        if(user != null){
            postEntity.setAuthorId(user);
        }
        if (!images.isEmpty()) {
            List<String> imageUrls = new ArrayList<>();
            for (MultipartFile multipartFile : images) {
                imageUrls.add(getFileUrls(multipartFile));
            }
            postEntity.setImageUrlList(imageUrls);
        }
        if(postDTO.getRoomDto() != null){
            postEntity.setRoomId(inforRoomMapper.dtoToEntity(postDTO.getRoomDto()));
        }
        postRepository.save(postEntity);
    }

    @Override
    public List<PostDtoWithRating> getPostByRating(Pageable pageable) {
        return postRepository.getPostByRating(pageable).stream().filter(postDtoWithRating -> !postDtoWithRating.getIsDeleted()).collect(Collectors.toList());
    }

    @Override
    public List<ProvinceDto> getTopOfProvince(Pageable pageable) {
        return postRepository.getTopOfProvince(pageable);
    }

    @Override
    public List<PostDto> getAllPost() {
        List<PostEntity> list = postRepository.findAll();
        return list.stream()
                .map(postEntity -> postMapper.entityToDto(postEntity))
                .collect(Collectors.toList());
    }

    @Override
    public PostDto getPostById(Long postId) {
        PostEntity post = postRepository.findById(postId).get();
//        return postMapper.entityToDto(postRepository.findById(postId).orElseThrow(EntityNotFoundException::new));
        return postMapper.entityToDto(post);
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
    public PostDto updatePost(PostDto postDto) {
        PostEntity post = postRepository.findById(postDto.getPostId()).get();
        post.setTitle(postDto.getTitle());
        post.setAddress(postDto.getAddress());
        post.setDescription(postDto.getDescription());
        post.setConvenient(postDto.getConvenient());
        post.setModifiedDate(new Date());
        post.setSurroundings(postDto.getSurroundings());
        post.setDeleted(postDto.isDeleted());
        PostEntity updatePost = postRepository.save(post);
        return postMapper.entityToDto(updatePost);
    }

    @Override
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    private String getFileUrls(MultipartFile multipartFile) throws IOException{
        return cloudinary.uploader()
                .upload(multipartFile.getBytes(), Map.of("public_id", UUID.randomUUID().toString()))
                .get("url")
                .toString();
    }
}
