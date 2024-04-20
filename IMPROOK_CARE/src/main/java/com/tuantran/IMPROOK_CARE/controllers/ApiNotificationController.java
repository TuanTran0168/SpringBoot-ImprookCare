/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.controllers;

import com.tuantran.IMPROOK_CARE.components.authentication.AuthenticationComponent;
import com.tuantran.IMPROOK_CARE.dto.AddMessageDTO;
import com.tuantran.IMPROOK_CARE.dto.AddNotificationDTO;
import com.tuantran.IMPROOK_CARE.models.Message;
import com.tuantran.IMPROOK_CARE.models.Notification;
import com.tuantran.IMPROOK_CARE.models.ProfileDoctor;
import com.tuantran.IMPROOK_CARE.models.User;
import com.tuantran.IMPROOK_CARE.repository.ProfileDoctorRepository;
import com.tuantran.IMPROOK_CARE.repository.UserRepository;
import com.tuantran.IMPROOK_CARE.service.MessageService;
import com.tuantran.IMPROOK_CARE.service.NotificationService;
import com.tuantran.IMPROOK_CARE.service.NotificationTypeService;
import com.tuantran.IMPROOK_CARE.service.UserService;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import jakarta.validation.Valid;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Administrator
 */
@RestController
@RequestMapping("/api")
public class ApiNotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    NotificationTypeService notificationTypeService;

    @Autowired
    UserService userService;

    @PostMapping("/auth/add-notification/")
    @CrossOrigin
    public ResponseEntity<?> addNotification(@Valid @RequestBody AddNotificationDTO addNotificationDTO) {
        try {
            String message = "Có lỗi xảy ra!";

            Notification notification = new Notification();

            User sender = this.userService
                    .findUserByUserIdAndActiveTrue(Integer.parseInt(addNotificationDTO.getSenderId()));

            if (sender != null) {
                notification.setSenderId(sender);
                User receiver = this.userService
                        .findUserByUserIdAndActiveTrue(Integer.parseInt(addNotificationDTO.getReceiverId()));
                if (receiver != null) {
                    notification.setReceiverId(receiver);
                    // Cái profile này còn khá lấn cấn ở case gửi Message từ phía profile
                    if (addNotificationDTO.getProfileDoctorId() != null) {
                        notification.setProfileDoctorId(Integer.parseInt(addNotificationDTO.getProfileDoctorId()));
                    }

                    notification.setNotificationTypeId(
                            this.notificationTypeService.findNotificationTypeByNotificationTypeIdAndActiveTrue(
                                    Integer.parseInt(addNotificationDTO.getNotificationTypeId())).get());

                    notification.setNotificationContent(addNotificationDTO.getNotificationContent());
                    notification.setCreatedDate(new Date());
                    notification.setActive(Boolean.TRUE);
                    notification.setIsSeen(Boolean.FALSE);

                    return new ResponseEntity<>(this.notificationService.addNotification(notification),
                            HttpStatus.CREATED);
                } else {
                    message = "Receiver[" + addNotificationDTO.getReceiverId() + "] không tồn tại!";
                    return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
                }

            } else {
                message = "Sender[" + addNotificationDTO.getSenderId() + "] không tồn tại!";
                return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
            }

        } catch (NullPointerException e) {
            return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
