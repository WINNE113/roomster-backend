package com.roomster.roomsterbackend.service.impl;

import com.roomster.roomsterbackend.entity.CommentEntity;
import com.roomster.roomsterbackend.repository.CommentRepository;
import com.roomster.roomsterbackend.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService implements ICommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Override
    public CommentEntity save(CommentEntity comment) {
        return commentRepository.saveAndFlush(comment);
    }

    @Override
    public void delete(CommentEntity comment) {
         commentRepository.delete(comment);
    }
}
