package com.library.service.impl;

import com.library.entity.Comment;
import com.library.repository.CommentRepository;
import com.library.service.CommentService;

import java.util.List;

public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;
    @Override
    public List<Comment> getAllComment() {
        return commentRepository.findAll();
    }
}
