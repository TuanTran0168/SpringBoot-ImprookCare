/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.service;

import com.tuantran.IMPROOK_CARE.dto.AddCommentDTO;
import com.tuantran.IMPROOK_CARE.dto.UpdateCommentDTO;
import com.tuantran.IMPROOK_CARE.models.Comment;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.query.Param;
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
    
    List<Object[]> checkComment(@Param("userId") int userId, @Param("profileDoctorId") int profileDoctorId);
    
    Page<Comment> findAllCommentPageSpec(Map<String, String> params);
    
    Page<Comment> findCommentByProfileDoctorIdPage(int profileDoctorId, Map<String, String> params);
}
