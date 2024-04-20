/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.service;

import java.util.Optional;

import com.tuantran.IMPROOK_CARE.models.NotificationType;

/**
 *
 * @author Administrator
 */
public interface NotificationTypeService {
    Optional<NotificationType> findNotificationTypeByNotificationTypeIdAndActiveTrue(int notificationId);
}
