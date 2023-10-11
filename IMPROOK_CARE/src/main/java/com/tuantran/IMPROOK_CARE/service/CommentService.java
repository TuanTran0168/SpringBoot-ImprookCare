/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.service;

import com.tuantran.IMPROOK_CARE.dto.AddCommentDTO;
import com.tuantran.IMPROOK_CARE.dto.UpdateCommentDTO;
import com.tuantran.IMPROOK_CARE.models.Comment;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Administrator
 */
public interface CommentService {

    int softDeleteComment(int commentId);

    Comment findCommentByCommentIdAndActiveTrue(int commentId);

    int addComment(AddCommentDTO addCommentDTO, MultipartFile avatar);

    int updateComment(UpdateCommentDTO updateCommentDTO, MultipartFile avatar);
}
