/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.service.Impl;

import com.tuantran.IMPROOK_CARE.models.Comment;
import com.tuantran.IMPROOK_CARE.repository.CommentRepository;
import com.tuantran.IMPROOK_CARE.service.CommentService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Administrator
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    @Transactional
    public int softDeleteComment(int commentId) {
        Optional<Comment> commentOptional = this.commentRepository.findCommentByCommentIdAndActiveTrue(commentId);
        if (commentOptional.isPresent()) {
            Comment comment = commentOptional.get();
            if (comment.getActive().equals(Boolean.TRUE)) {
                comment.setActive(Boolean.FALSE);
                return 1;
            } else {
                System.out.println("Comment: " + comment.getActive());
                return 2;
            }
        } else {
            return 3; // Không tìm được để xóa
        }
    }

    @Override
    public Comment findCommentByCommentIdAndActiveTrue(int commentId) {
        Optional<Comment> commentOptional = this.commentRepository.findCommentByCommentIdAndActiveTrue(commentId);

        if (commentOptional.isPresent()) {
            return commentOptional.get();
        } else {
            return null;
        }
    }

}
