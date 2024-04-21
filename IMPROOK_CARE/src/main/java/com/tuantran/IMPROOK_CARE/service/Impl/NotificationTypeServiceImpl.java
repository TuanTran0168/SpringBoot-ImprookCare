/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.service.Impl;

import com.tuantran.IMPROOK_CARE.models.NotificationType;
import com.tuantran.IMPROOK_CARE.repository.NotificationTypeRepository;
import com.tuantran.IMPROOK_CARE.service.NotificationTypeService;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Administrator
 */
@Service
public class NotificationTypeServiceImpl implements NotificationTypeService {

    @Autowired
    private NotificationTypeRepository notificationTypeRepository;

    @Override
    public Optional<NotificationType> findNotificationTypeByNotificationTypeIdAndActiveTrue(int notificationId) {
        return this.notificationTypeRepository.findNotificationTypeByNotificationTypeIdAndActiveTrue(notificationId);
    }

}
