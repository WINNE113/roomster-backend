package com.roomster.roomsterbackend.service;

import com.roomster.roomsterbackend.entity.CommentEntity;

public interface ICommentService {
    public CommentEntity save(CommentEntity comment);

    public void delete(CommentEntity comment);
}
