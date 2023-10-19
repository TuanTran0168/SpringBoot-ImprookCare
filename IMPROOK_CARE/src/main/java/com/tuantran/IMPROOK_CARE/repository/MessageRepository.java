/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.repository;

import com.tuantran.IMPROOK_CARE.models.Message;
import com.tuantran.IMPROOK_CARE.models.ProfileDoctor;
import com.tuantran.IMPROOK_CARE.models.User;
import java.util.List;
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
public interface MessageRepository extends JpaRepository<Message, Integer> {

    @Query("SELECT m.userId, m.profileDoctorId "
            + "FROM Message m "
            + "WHERE m.userId.userId = :userId AND m.profileDoctorId.profileDoctorId = :profileDoctorId")
    Page<Message> getMessagesBetweenUsersAndProfileDoctors(@Param("userId") int userId, @Param("profileDoctorId") int profileDoctorId, Pageable page);

    Page<Message> findMessagesByUserIdAndProfileDoctorId(User userId, ProfileDoctor profileDoctorId, Pageable page);

    List<Message> findAll(Specification<Message> createSpecification);

    @Query("SELECT DISTINCT m.userId FROM Message m WHERE m.profileDoctorId.profileDoctorId = :profileDoctorId")
//    @Query("SELECT m.userId.userId FROM Message m WHERE m.profileDoctorId.profileDoctorId = :profileDoctorId GROUP BY m.userId.userId")
    Page<Object[]> getAllUsersByProfileDoctorMessaging(@Param("profileDoctorId") int profileDoctorId, Pageable page);
}
