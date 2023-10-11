/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.service.Impl;

import com.tuantran.IMPROOK_CARE.components.cloudinary.CloudinaryComponent;
import com.tuantran.IMPROOK_CARE.dto.AddCommentDTO;
import com.tuantran.IMPROOK_CARE.dto.UpdateCommentDTO;
import com.tuantran.IMPROOK_CARE.models.Comment;
import com.tuantran.IMPROOK_CARE.models.ProfileDoctor;
import com.tuantran.IMPROOK_CARE.models.User;
import com.tuantran.IMPROOK_CARE.repository.CommentRepository;
import com.tuantran.IMPROOK_CARE.repository.ProfileDoctorRepository;
import com.tuantran.IMPROOK_CARE.repository.UserRepository;
import com.tuantran.IMPROOK_CARE.service.CommentService;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Administrator
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileDoctorRepository profileDoctorRepository;

    @Autowired
    private CloudinaryComponent cloudinaryComponent;

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

    @Override
    public int addComment(AddCommentDTO addCommentDTO, MultipartFile avatar) {
        try {
            Comment comment = new Comment();

            Optional<User> userOptional = this.userRepository.findUserByUserIdAndActiveTrue(Integer.parseInt(addCommentDTO.getUserId()));

            if (userOptional.isPresent()) {
                comment.setUserId(userOptional.get());
            } else {
                return 0;
            }

            Optional<ProfileDoctor> profileDoctorOptional = this.profileDoctorRepository.findProfileDoctorByProfileDoctorIdAndActiveTrue(Integer.parseInt(addCommentDTO.getProfuleDoctorId()));

            if (profileDoctorOptional.isPresent()) {
                comment.setProfileDoctorId(profileDoctorOptional.get());
            } else {
                return 0;
            }

            comment.setContent(addCommentDTO.getContent());
            comment.setRating(Integer.parseInt(addCommentDTO.getRating()));

            if (avatar != null && !avatar.isEmpty()) {
                String linkCloudinaryAvatar = cloudinaryComponent.Cloudinary(avatar).get("secure_url").toString();
                comment.setAvatar(linkCloudinaryAvatar);
            }

            comment.setActive(Boolean.TRUE);
            comment.setCreatedDate(new Date());
            this.commentRepository.save(comment);
            return 1;
        } catch (DataAccessException ex) {
            ex.printStackTrace();
            return 0;
        } catch (NoSuchElementException ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    @Override
    public int updateComment(UpdateCommentDTO updateCommentDTO, MultipartFile avatar) {
        try {
            Optional<Comment> commentOptional = this.commentRepository.findCommentByCommentIdAndActiveTrue(Integer.parseInt(updateCommentDTO.getCommentId()));
            if (commentOptional.isPresent()) {
                Comment comment = commentOptional.get();
                comment.setContent(updateCommentDTO.getContent());
                comment.setRating(Integer.parseInt(updateCommentDTO.getRating()));
                this.commentRepository.save(comment);
                return 1;
            } else {
                return 2;
            }
        } catch (DataAccessException ex) {
            ex.printStackTrace();
            return 0;
        } catch (NoSuchElementException ex) {
            ex.printStackTrace();
            return 0;
        }
    }

}
