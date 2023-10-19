/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.service.Impl;

import com.tuantran.IMPROOK_CARE.Specifications.GenericSpecifications;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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

    @Autowired
    private Environment environment;

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
    @Transactional
    public int addComment(AddCommentDTO addCommentDTO, MultipartFile avatar) {
        try {

            List<Object[]> listCheckComment = this.checkComment(Integer.parseInt(addCommentDTO.getUserId()), Integer.parseInt(addCommentDTO.getProfileDoctorId()));
            if (listCheckComment == null || listCheckComment.isEmpty()) {
                return 2; // Chưa khám miễn bình luận
            }
            Comment comment = new Comment();

            Optional<User> userOptional = this.userRepository.findUserByUserIdAndActiveTrue(Integer.parseInt(addCommentDTO.getUserId()));

            if (userOptional.isPresent()) {
                comment.setUserId(userOptional.get());
            } else {
                return 0;
            }

            Optional<ProfileDoctor> profileDoctorOptional = this.profileDoctorRepository.findProfileDoctorByProfileDoctorIdAndActiveTrue(Integer.parseInt(addCommentDTO.getProfileDoctorId()));

            if (profileDoctorOptional.isPresent()) {
                ProfileDoctor profileDoctor = profileDoctorOptional.get();
                String currentTotalRating = profileDoctor.getTotalRating();
                String newRating = addCommentDTO.getRating();
                profileDoctor.setTotalRating(String.valueOf(Integer.parseInt(currentTotalRating) + Integer.parseInt(newRating)));

                String currentCountRating = profileDoctor.getCountRating();
                profileDoctor.setCountRating(String.valueOf(Integer.parseInt(currentCountRating) + 1));

                comment.setProfileDoctorId(profileDoctor);
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
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    @Override
    @Transactional
    public int updateComment(UpdateCommentDTO updateCommentDTO, MultipartFile avatar) {
        try {
            Optional<Comment> commentOptional = this.commentRepository.findCommentByCommentIdAndActiveTrue(Integer.parseInt(updateCommentDTO.getCommentId()));
            if (commentOptional.isPresent()) {
                Comment comment = commentOptional.get();
                if (comment.getUserId().getUserId().equals(Integer.parseInt(updateCommentDTO.getUserId()))) {
                    comment.setContent(updateCommentDTO.getContent());

                    int oldRatingOfComment = comment.getRating();

                    Optional<ProfileDoctor> profileDoctorOptional = this.profileDoctorRepository.findProfileDoctorByProfileDoctorIdAndActiveTrue(comment.getProfileDoctorId().getProfileDoctorId());
                    if (!profileDoctorOptional.isPresent()) {
                        return 0;
                    }

                    ProfileDoctor profileDoctor = profileDoctorOptional.get();
                    String oldTotalRaitingOfProfileDoctor = profileDoctor.getTotalRating();

                    profileDoctor.setTotalRating(String.valueOf((Integer.parseInt(oldTotalRaitingOfProfileDoctor) - oldRatingOfComment) + Integer.parseInt(updateCommentDTO.getRating())));

                    comment.setRating(Integer.parseInt(updateCommentDTO.getRating()));

                    if (avatar != null && !avatar.isEmpty()) {
                        String linkCloudinaryAvatar = cloudinaryComponent.Cloudinary(avatar).get("secure_url").toString();
                        comment.setAvatar(linkCloudinaryAvatar);
                    }
                    comment.setUpdatedDate(new Date());
                    this.commentRepository.save(comment);
                    return 1;
                } else {
                    return 3;
                }
            } else {
                return 2; // Không tìm thấy bình luận
            }
        } catch (DataAccessException ex) {
            ex.printStackTrace();
            return 0;
        } catch (NoSuchElementException ex) {
            ex.printStackTrace();
            return 0;
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<Object[]> checkComment(int userId, int doctorId) {
        return this.commentRepository.getDetailsWhenUserHavePrescriptions(userId, doctorId);
    }

    @Override
    public Page<Comment> findAllCommentPageSpec(Map<String, String> params) {
        String pageNumber = params.get("pageNumber");
        String profileDoctorId = params.get("profileDoctorId");
        String rating = params.get("rating");

        List<Specification<Comment>> listSpec = new ArrayList<>();
        int defaultPageNumber = 0;
        Sort mySort = Sort.by("createdDate").descending();
        Pageable page = PageRequest.of(defaultPageNumber, Integer.parseInt(this.environment.getProperty("spring.data.web.pageable.default-page-size")), mySort);
        if (pageNumber != null && !pageNumber.isEmpty()) {
            if (!pageNumber.equals("NaN")) {
                page = PageRequest.of(Integer.parseInt(pageNumber), Integer.parseInt(this.environment.getProperty("spring.data.web.pageable.default-page-size")), mySort);
            }
        }

        if (profileDoctorId != null && !profileDoctorId.isEmpty()) {
            Specification<Comment> spec = GenericSpecifications.fieldContains("profileDoctorId", profileDoctorId);
            listSpec.add(spec);
        }

        if (rating != null && !rating.isEmpty()) {
            Specification<Comment> spec = GenericSpecifications.fieldContains("rating", rating);
            listSpec.add(spec);
        }

        Specification<Comment> spec = GenericSpecifications.fieldEquals("active", Boolean.TRUE);
        listSpec.add(spec);

        return this.commentRepository.findAll(GenericSpecifications.createSpecification(listSpec), page);
    }

    @Override
    public Page<Comment> findCommentByProfileDoctorIdPage(int profileDoctorId, Map<String, String> params) {
        try {
            Optional<ProfileDoctor> profileDoctorOptional = this.profileDoctorRepository.findProfileDoctorByProfileDoctorIdAndActiveTrue(profileDoctorId);

            if (profileDoctorOptional.isPresent()) {
                String pageNumber = params.get("pageNumber");
                String sortDate = params.get("sortDate");
                String sortRating = params.get("sortRating");

                int defaultPageNumber = 0;
                Sort mySort = Sort.by("createdDate").descending();

                if (sortDate != null && !sortDate.isEmpty()) {
                    if (sortDate.equals("asc")) {
                        mySort = Sort.by("createdDate").ascending();
                    } else if (sortDate.equals("des")) {
                        mySort = Sort.by("createdDate").descending();
                    } else {
                        System.out.println("sortDate not found!");
                    }
                }

                if (sortRating != null && !sortRating.isEmpty()) {
                    if (sortRating.equals("asc")) {
                        mySort = Sort.by("rating").ascending();
                    } else if (sortRating.equals("des")) {
                        mySort = Sort.by("rating").descending();
                    } else {
                        System.out.println("sortRating not found!");
                    }
                }

                Pageable page = PageRequest.of(defaultPageNumber, Integer.parseInt(this.environment.getProperty("spring.data.web.pageable.default-page-size")), mySort);

                if (pageNumber != null && !pageNumber.isEmpty()) {
                    if (!pageNumber.equals("NaN")) {
                        page = PageRequest.of(Integer.parseInt(pageNumber), Integer.parseInt(this.environment.getProperty("spring.data.web.pageable.default-page-size")), mySort);
                    }
                }

                ProfileDoctor profileDoctor = profileDoctorOptional.get();
                return this.commentRepository.findAllCommentByProfileDoctorIdAndActiveTrue(profileDoctor, page);
            }
            return null;
        } catch (NoSuchElementException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
