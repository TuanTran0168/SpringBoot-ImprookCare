/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.repository;

import com.tuantran.IMPROOK_CARE.models.Notification;
import com.tuantran.IMPROOK_CARE.models.User;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Administrator
 */
@Repository
@Transactional
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    Optional<Notification> findNotificationByNotificationId(int notificationId);

    // Lấy toàn bộ thông báo có id người nhận là user đang login
    Page<Notification> findNotificationByReceiverIdAndActiveTrue(User userId, Pageable page);
}
