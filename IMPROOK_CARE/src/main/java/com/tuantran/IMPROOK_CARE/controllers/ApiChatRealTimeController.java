package com.tuantran.IMPROOK_CARE.controllers;

import com.tuantran.IMPROOK_CARE.dto.AddMessageDTO;
import com.tuantran.IMPROOK_CARE.dto.ChatRealTimeMessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("/api")
public class ApiChatRealTimeController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    public ChatRealTimeMessageDTO receiveMessage(@Payload ChatRealTimeMessageDTO message) {
        return message;
    }

//    @MessageMapping("/private-message")
//    public ChatRealTimeMessageDTO recMessage(@Payload ChatRealTimeMessageDTO message) {
//        simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(), "/private", message);
//        System.out.println(message.toString());
//        return message;
//    }

    @MessageMapping("/private-message")
    public AddMessageDTO recMessage(@Payload AddMessageDTO message) {
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
}
