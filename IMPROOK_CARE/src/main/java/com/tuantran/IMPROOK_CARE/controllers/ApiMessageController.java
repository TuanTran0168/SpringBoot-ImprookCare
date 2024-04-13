/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.controllers;

import com.tuantran.IMPROOK_CARE.dto.AddMessageDTO;
import com.tuantran.IMPROOK_CARE.models.Message;
import com.tuantran.IMPROOK_CARE.models.ProfileDoctor;
import com.tuantran.IMPROOK_CARE.models.User;
import com.tuantran.IMPROOK_CARE.repository.ProfileDoctorRepository;
import com.tuantran.IMPROOK_CARE.repository.UserRepository;
import com.tuantran.IMPROOK_CARE.service.MessageService;

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
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Administrator
 */
@RestController
@RequestMapping("/api")
public class ApiMessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileDoctorRepository profileDoctorRepository;

    @PostMapping("/auth/add-message/")
    @CrossOrigin
    public ResponseEntity<?> addMessage(@Valid AddMessageDTO addMessageDTO,
            @RequestPart("avatar") MultipartFile avatar) {
        String message = "Có lỗi xảy ra!";

        Message messageData = new Message();
        Optional<User> userOptional = this.userRepository
                .findUserByUserIdAndActiveTrue(Integer.parseInt(addMessageDTO.getUserId()));

        if (userOptional.isPresent()) {
            messageData.setUserId(userOptional.get());

            Optional<ProfileDoctor> profileDoctorOptional = this.profileDoctorRepository
                    .findProfileDoctorByProfileDoctorIdAndActiveTrue(
                            Integer.parseInt(addMessageDTO.getProfileDoctorId()));

            if (profileDoctorOptional.isPresent()) {
                messageData.setProfileDoctorId(profileDoctorOptional.get());
                messageData.setSenderId(Integer.parseInt(addMessageDTO.getSenderId()));
                messageData.setMessageContent(addMessageDTO.getMessageContent());
                messageData.setActive(Boolean.TRUE);
                messageData.setCreatedDate(new Date());

                return new ResponseEntity<>(this.messageService.addMessage(messageData, avatar), HttpStatus.CREATED);
            } else {
                message = "ProfileDoctor[" + addMessageDTO.getProfileDoctorId() + "] không tồn tại!";
                return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
            }
        } else {
            message = "User[" + addMessageDTO.getUserId() + "] không tồn tại!";
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/auth/get-message/")
    @CrossOrigin
    public ResponseEntity<?> getMessage(@RequestBody Map<String, String> params) {
        return new ResponseEntity<>(this.messageService.getMessagesBetweenUsersAndProfileDoctors(params),
                HttpStatus.OK);
    }

    @GetMapping("/auth/profileDoctor/{profileDoctorId}/get-message-detail-page/{userId}/")
    @CrossOrigin
    public ResponseEntity<?> getMessageAllView(@PathVariable(value = "profileDoctorId") int profileDoctorId,
            @PathVariable(value = "userId") int userId, @RequestParam Map<String, String> params) {
        return new ResponseEntity<>(
                this.messageService.findMessagesByUserIdAndProfileDoctorIdPage(userId, profileDoctorId, params),
                HttpStatus.OK);
    }

    @GetMapping("/auth/profileDoctor/{profileDoctorId}/get-message-detail/{userId}/")
    @CrossOrigin
    public ResponseEntity<?> getMessageAllView(@PathVariable(value = "profileDoctorId") int profileDoctorId,
            @PathVariable(value = "userId") int userId) {
        return new ResponseEntity<>(this.messageService.findMessagesByUserIdAndProfileDoctorId(userId, profileDoctorId),
                HttpStatus.OK);
    }

    @GetMapping("/auth/profileDoctor/{profileDoctorId}/get-user-send-message-to-doctor/")
    @CrossOrigin
    public ResponseEntity<?> getUserSendMessageToDoctor(@PathVariable(value = "profileDoctorId") int profileDoctorId,
            @RequestParam Map<String, String> params) {
        return new ResponseEntity<>(this.messageService.getAllUsersByProfileDoctorMessaging(profileDoctorId, params),
                HttpStatus.OK);
    }
}
