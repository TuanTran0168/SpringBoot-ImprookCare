package com.tuantran.IMPROOK_CARE.controllers;

import com.tuantran.IMPROOK_CARE.dto.AddMessageSocketDTO;
import com.tuantran.IMPROOK_CARE.dto.AddNotificationSocketDTO;
import com.tuantran.IMPROOK_CARE.dto.ChatRealTimeMessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
// @RequestMapping("/api")
public class ApiRealTimeController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    public ChatRealTimeMessageDTO receiveMessage(@Payload ChatRealTimeMessageDTO message) {
        return message;
    }

    @MessageMapping("/private-message")
    public AddMessageSocketDTO recMessage(@Payload AddMessageSocketDTO message) {
        if (message.getSenderId().equals(message.getProfileDoctorId())) {
            simpMessagingTemplate.convertAndSendToUser(message.getUserId(), "/private", message);
            System.out.println("1");
        } else {
            simpMessagingTemplate.convertAndSendToUser(message.getProfileDoctorId(), "/private", message);
            System.out.println("2");
        }

        System.out.println(message.toString());
        return message;
    }

    @MessageMapping("/private-notification")
    public AddNotificationSocketDTO notification(@Payload AddNotificationSocketDTO notification) {
        simpMessagingTemplate.convertAndSendToUser(notification.getReceiverId(), "/notification", notification);

        System.out.println(notification.toString());
        return notification;
    }
}
