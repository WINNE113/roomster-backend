package com.roomster.roomsterbackend.service.impl;

import com.roomster.roomsterbackend.dto.CommentPostDto;
import com.roomster.roomsterbackend.entity.Comment;
import com.roomster.roomsterbackend.mapper.CommentMapper;
import com.roomster.roomsterbackend.repository.CommentPostRepository;
import com.roomster.roomsterbackend.repository.PostRepository;
import com.roomster.roomsterbackend.repository.UserRepository;
import com.roomster.roomsterbackend.service.IService.ICommentPostService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CommentPostService implements ICommentPostService {
    @Autowired
    CommentPostRepository commentPostRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    CommentMapper commentMapper;
    @Override
    public CommentPostDto saveNewComment(CommentPostDto commentPostDTO) {
        return commentMapper.entityToDTO(commentPostRepository.save(commentMapper.dtoToEntity(commentPostDTO)));
    }

    @Override
    public CommentPostDto updateComment(CommentPostDto commentPostDTO) {
        Comment oldComment = commentPostRepository.findById(commentPostDTO.getCommentPostId()).orElseThrow(EntityNotFoundException::new);
        return commentMapper.entityToDTO(
                commentPostRepository.save(
                        commentMapper.updateCommentPost(oldComment,commentMapper.dtoToEntity(commentPostDTO))
                )
        );
    }

    @Override
    public void deleteComment(CommentPostDto commentPostDTO) {
        commentPostRepository.delete(
                commentMapper.dtoToEntity(commentPostDTO)
        );
    }

    @Override
    public List<CommentPostDto> getAllCommentOfPost(Long postId) {
        return commentPostRepository.getCommentByPostId(postId)
                .stream().map(commentPostEntity -> commentMapper.entityToDTO(commentPostEntity))
                .collect(Collectors.toList());
    }
}
