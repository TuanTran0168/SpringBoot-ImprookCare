/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.service.Impl;

import com.tuantran.IMPROOK_CARE.models.Notification;
import com.tuantran.IMPROOK_CARE.models.User;
import com.tuantran.IMPROOK_CARE.repository.NotificationRepository;
import com.tuantran.IMPROOK_CARE.service.NotificationService;

import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author Administrator
 */
@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private Environment environment;

    @Override
    public Notification addNotification(Notification notification) {
        return this.notificationRepository.save(notification);
    }

    // Lấy toàn bộ thông báo có id người nhận là user đang login
    // @Cacheable(value = "notification")
    @Override
    public Page<Notification> findNotificationByReceiverIdAndActiveTrue(User receiverId, Map<String, String> params) {
        String pageNumber = params.get("pageNumber");
        int defaultPageNumber = 0;

        Pageable page = PageRequest.of(defaultPageNumber,
                Integer.parseInt(this.environment.getProperty("spring.data.web.pageable.default-page-size")));

        if (pageNumber != null && !pageNumber.isEmpty()) {
            page = PageRequest.of(Integer.parseInt(pageNumber),
                    Integer.parseInt(this.environment.getProperty("spring.data.web.pageable.default-page-size")));
        }

        return this.notificationRepository.findNotificationByReceiverIdAndActiveTrueOrderByCreatedDateDesc(receiverId,
                page);
    }

    @Override
    public Notification seenNotification(Notification notification) {
        return this.notificationRepository.save(notification);
    }

    @Override
    public Optional<Notification> findNotificationByNotificationIdAndActiveTrue(int notificationId) {
        return this.notificationRepository.findNotificationByNotificationIdAndActiveTrue(notificationId);
    }

}
