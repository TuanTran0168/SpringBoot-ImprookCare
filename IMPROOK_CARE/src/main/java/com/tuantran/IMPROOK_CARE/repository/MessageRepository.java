/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.repository;

import com.tuantran.IMPROOK_CARE.models.Message;
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
public interface MessageRepository extends JpaRepository<Message, Integer> {

        @Query("SELECT m.userId, m.profileDoctorId "
                        + "FROM Message m "
                        + "WHERE m.userId.userId = :userId AND m.profileDoctorId.profileDoctorId = :profileDoctorId")
        Page<Message> getMessagesBetweenUsersAndProfileDoctors(@Param("userId") int userId,
                        @Param("profileDoctorId") int profileDoctorId, Pageable page);

        Page<Message> findMessagesByUserIdAndProfileDoctorId(User userId, ProfileDoctor profileDoctorId, Pageable page);

        List<Message> findAll(Specification<Message> createSpecification);

        /*
         * Lấy toàn bộ thông tin User có nhắn tin với ProfileDoctor
         * Kèm theo tin nhắn cuối cùng giữa 2 người này
         */
        @Query("SELECT m.userId, m.messageId, m.senderId, m.messageContent, m.createdDate, m.isSeen "
                        + "FROM Message m "
                        + "WHERE m.messageId IN (SELECT MAX(m2.messageId) FROM Message m2 WHERE m2.userId = m.userId) "
                        + "AND m.profileDoctorId.profileDoctorId = :profileDoctorId "
                        + "ORDER BY m.createdDate DESC")
        Page<?> getAllUsersByProfileDoctorMessaging(@Param("profileDoctorId") int profileDoctorId,
                        Pageable page);

        Optional<Message> findMessageByMessageIdAndActiveTrue(int messageId);

        /*
         * Lấy toàn bộ thông tin ProfileDoctor có nhắn tin với User
         * Kèm theo tin nhắn cuối cùng giữa 2 người này
         */
        // @Query("SELECT DISTINCT pd "
        // + "FROM Message m "
        // + "JOIN m.profileDoctorId pd "
        // + "JOIN pd.userId u "
        // + "WHERE m.userId.userId = :userId "
        // + "ORDER BY pd.createdDate DESC")
        @Query("SELECT m.profileDoctorId, m.messageId, m.senderId, m.messageContent, m.createdDate, m.isSeen "
                        + "FROM Message m "
                        + "JOIN m.profileDoctorId pd "
                        + "JOIN m.userId u "
                        + "WHERE m.userId.userId = :userId "
                        + "AND m.messageId IN (SELECT MAX(m2.messageId) FROM Message m2 WHERE m2.profileDoctorId = pd) "
                        + "ORDER BY m.createdDate DESC")
        Page<?> getMessageProfileDoctorByUserIdPage(@Param("userId") int userId, Pageable page);

        // Page<Message> getUsersAndFirstMessagePage(@Param("userId") int userId,
        // @Param("profileDoctorId") int profileDoctorId,
        // Pageable page);
}
