/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.repository;

import com.tuantran.IMPROOK_CARE.models.ProfileDoctor;
import com.tuantran.IMPROOK_CARE.models.User;
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
public interface ProfileDoctorRepository extends JpaRepository<ProfileDoctor, Integer> {

    Optional<ProfileDoctor> findProfileDoctorByProfileDoctorIdAndActiveTrue(int profileDoctorId);

    List<ProfileDoctor> findAllProfileDoctorByActiveTrue();

    List<ProfileDoctor> findProfileDoctorByUserIdAndActiveTrue(User userId);

    Page<ProfileDoctor> findAll(Specification<ProfileDoctor> createSpecification, Pageable page);

    // Lấy thông tin ProfileDoctor nào nhắn tin với userId
    @Query("SELECT DISTINCT pd "
            + "FROM Message m "
            + "JOIN m.profileDoctorId pd "
            + "JOIN pd.userId u "
            + "WHERE m.userId.userId = :userId "
            + "ORDER BY pd.createdDate DESC")
    Page<ProfileDoctor> getMessageProfileDoctorByUserIdPage(@Param("userId") int userId, Pageable page);
}
