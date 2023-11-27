package com.roomster.roomsterbackend.service.impl;

import com.cloudinary.Cloudinary;
import com.roomster.roomsterbackend.common.Status;
import com.roomster.roomsterbackend.dto.post.*;
import com.roomster.roomsterbackend.entity.PostEntity;
import com.roomster.roomsterbackend.entity.PostTypeEntity;
import com.roomster.roomsterbackend.entity.UserEntity;
import com.roomster.roomsterbackend.mapper.InforRoomMapper;
import com.roomster.roomsterbackend.mapper.PostMapper;
import com.roomster.roomsterbackend.mapper.UserMapper;
import com.roomster.roomsterbackend.repository.PostRepository;
import com.roomster.roomsterbackend.repository.PostTypeRepository;
import com.roomster.roomsterbackend.repository.UserRepository;
import com.roomster.roomsterbackend.service.IService.IPostService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService implements IPostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private InforRoomMapper inforRoomMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostTypeRepository postTypeRepository;

    @Autowired
    private UserMapper userMapper;

    private final Cloudinary cloudinary;
    @Override
    public List<PostDto> getPostsApproved(Pageable pageable) {
        List<PostEntity> postPage = postRepository.getAllByStatusAndIsDeleted(pageable, Status.APPROVED, false);
        return  postPage.stream()
                .map(postEntity -> postMapper.entityToDto(postEntity))
                .toList();
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
    public void upsertPost(PostDto postDTO, List<MultipartFile> images, Principal connectedUser) throws IOException {
        PostEntity postEntity = postMapper.dtoToEntity(postDTO);
        postEntity.setPostType(postTypeRepository.getPostEntityByCode(postDTO.getPost_type()));
        postEntity.setDeleted(false);
        // need admin or sp admin accept to APPROVED post
        postEntity.setStatus(Status.REVIEW);
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
    public PostDetailDtoImp getPostDetail(Long postId) {
        Optional<PostDetailDto> postDetailDto = postRepository.getPostDetail(postId);
        PostDetailDtoImp postDetailDtoImp = new PostDetailDtoImp();
        if(postDetailDto.isPresent()){
            userRepository.getUserEntityByUserId(postDetailDto.get().getCreatedBy());
            convertPostDetail(postDetailDtoImp, postDetailDto);
        }
        return postDetailDtoImp;
    }

    private void convertPostDetail(PostDetailDtoImp postDetailDtoImp, Optional<PostDetailDto> postDetailDto) {
        postDetailDtoImp.setId(postDetailDto.get().getId());
        postDetailDtoImp.setTitle(postDetailDto.get().getTitle());
        postDetailDtoImp.setAddress(postDetailDto.get().getAddress());
        postDetailDtoImp.setDescription(postDetailDto.get().getDescription());
        postDetailDtoImp.setObject(postDetailDto.get().getObject());
        postDetailDtoImp.setConvenient(postDetailDto.get().getConvenient());
        postDetailDtoImp.setSurroundings(postDetailDto.get().getSurroundings());

        Optional<PostTypeEntity> postType = postTypeRepository.findById(postDetailDto.get().getPostType());
        postType.ifPresent(postTypeEntity -> postDetailDtoImp.setPostType(postTypeEntity.getName()));

        Optional<UserEntity> user = userRepository.findById(postDetailDto.get().getCreatedBy());
        user.ifPresent(userEntity -> postDetailDtoImp.setCreatedBy(userMapper.entityToDto(user.get())));

        postDetailDtoImp.setCreatedDate(postDetailDto.get().getCreatedDate());
        postDetailDtoImp.setRotation(postDetailDto.get().getRotation());
        postDetailDtoImp.setAcreage(postDetailDto.get().getAcreage());
        postDetailDtoImp.setElectricityPrice(postDetailDto.get().getElectricityPrice());
        postDetailDtoImp.setPrice(postDetailDto.get().getPrice());
        postDetailDtoImp.setWaterPrice(postDetailDto.get().getWaterPrice());
        postDetailDtoImp.setStayMax(postDetailDto.get().getStayMax());
    }

    @Override
    public List<PostImageDto> getPostImages(Long postId) {
        return postRepository.getPostImages(postId);
    }

    @Override
    public void deletePostById(Long postId) {
        postRepository.deleteById(postId);
    }

    @Override
    public void setIsApprovedPosts(Long[] listPostId) {
        for (Long item: listPostId
             ) {
            Optional<PostEntity> post = postRepository.findById(item);
            if(post.isPresent()){
                post.get().setStatus(Status.APPROVED);
                postRepository.save(post.get());
            }
        }
    }

    @Override
    public List<PostDto> getPostsReview(Pageable pageable) {
        List<PostEntity> postPage = postRepository.getAllByStatusAndIsDeleted(pageable, Status.REVIEW, false);
        // Get the content (posts) from the page
        return  postPage.stream()
                .map(postEntity -> postMapper.entityToDto(postEntity))
                .toList();
    }

    @Override
    public List<PostDto> getPostsRejected(Pageable pageable) {
        List<PostEntity> postPage = postRepository.getAllByStatusAndIsDeleted(pageable, Status.REJECTED, false);
        return  postPage.stream()
                .map(postEntity -> postMapper.entityToDto(postEntity))
                .toList();
    }

    @Override
    public void setIsRejectedPosts(Long[] listPostId) {
        for (Long item: listPostId
        ) {
            Optional<PostEntity> post = postRepository.findById(item);
            if(post.isPresent()){
                post.get().setStatus(Status.REJECTED);
                postRepository.save(post.get());
            }
        }
    }

    private String getFileUrls(MultipartFile multipartFile) throws IOException{
        return cloudinary.uploader()
                .upload(multipartFile.getBytes(), Map.of("public_id", UUID.randomUUID().toString()))
                .get("url")
                .toString();
    }
}
