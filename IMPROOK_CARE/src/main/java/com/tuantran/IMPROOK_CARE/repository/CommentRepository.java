/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.repository;

import com.tuantran.IMPROOK_CARE.models.Comment;
import com.tuantran.IMPROOK_CARE.models.ProfileDoctor;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Administrator
 */
@Repository
@Transactional
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    Optional<Comment> findCommentByCommentIdAndActiveTrue(int commentId);

    List<Comment> findCommentByProfileDoctorIdAndActiveTrue(ProfileDoctor profileDoctorId);

    // Dùng để check coi thằng user có userId này có khám chưa để cho nó comment
    @Query("SELECT u.userId, u.lastname, u.firstname, pd.profileDoctorId, pd.name, p.prescriptionId, p.diagnosis, s.profileDoctorId "
            + "FROM Prescriptions p "
            + "JOIN p.bookingId b "
            + "JOIN b.profilePatientId pp "
            + "JOIN pp.userId u "
            + "JOIN b.scheduleId s "
            + "JOIN s.profileDoctorId pd "
            + "WHERE u.userId = :userId "
            + "AND pd.profileDoctorId = :profileDoctorId")
    List<Object[]> getDetailsWhenUserHavePrescriptions(@Param("userId") int userId, @Param("profileDoctorId") int profileDoctorId);

    Page<Comment> findAll(Specification<Comment> createSpecification, Pageable page);

    Page<Comment> findAllCommentByProfileDoctorIdAndActiveTrue(ProfileDoctor profileDoctorId, Pageable page);
}
