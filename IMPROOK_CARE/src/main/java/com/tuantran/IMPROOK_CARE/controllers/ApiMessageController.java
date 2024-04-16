/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.controllers;

import com.tuantran.IMPROOK_CARE.components.authentication.AuthenticationComponent;
import com.tuantran.IMPROOK_CARE.dto.AddMessageDTO;
import com.tuantran.IMPROOK_CARE.models.Message;
import com.tuantran.IMPROOK_CARE.models.ProfileDoctor;
import com.tuantran.IMPROOK_CARE.models.User;
import com.tuantran.IMPROOK_CARE.repository.ProfileDoctorRepository;
import com.tuantran.IMPROOK_CARE.repository.UserRepository;
import com.tuantran.IMPROOK_CARE.service.MessageService;
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

    @Autowired
    private AuthenticationComponent authenticationComponent;

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
                messageData.setIsSeen(Boolean.FALSE);
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
    public ResponseEntity<?> getMessageAllViewPage(@PathVariable(value = "profileDoctorId") int profileDoctorId,
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

    /*
     * API này dùng để đánh dấu isSeen rằng client đã xem tin nhắn
     */
    @PostMapping("/auth/message/{messageId}/seenMessage/")
    @CrossOrigin
    public ResponseEntity<?> seenMessage(@PathVariable(value = "messageId") int messageId) {
        String message = "Có lỗi xảy ra!";

        Optional<Message> messageOptional = this.messageService.findMessageByMessageIdAndActiveTrue(messageId);
        if (messageOptional.isPresent()) {
            Message messageData = messageOptional.get();
            messageData.setIsSeen(Boolean.TRUE);
            return new ResponseEntity<>(this.messageService.seenMessage(messageData),
                    HttpStatus.OK);
        } else {
            message = "Message[" + messageId + "] không tồn tại!";
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }

    }

    // Thử phân quyền xíu
    // Chỉ user nào login khớp với userId truyền vào mới được fetch
    /*
     * Lấy toàn bộ thông tin ProfileDoctor có nhắn tin với User
     * Kèm theo tin nhắn cuối cùng giữa 2 người này
     */
    // @GetMapping("/auth/user/{userId}/profile-doctor-message/")
    @GetMapping("/auth/user/{userId}/get-doctor-send-message-to-user/")
    @CrossOrigin
    public ResponseEntity<?> getMessageProfileDoctorByUserIdPage(@PathVariable(value = "userId") String userId,
            @RequestParam Map<String, String> params) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            System.out.print(authentication.getPrincipal());

            if (this.authenticationComponent.isUserAuthorized(authentication, userId)) {
                return new ResponseEntity<>(
                        this.messageService.getMessageProfileDoctorByUserIdPage(Integer.parseInt(userId), params),
                        HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Unauthorized!", HttpStatus.UNAUTHORIZED);
            }
        } catch (NullPointerException e) {
            return new ResponseEntity<>("User[" + userId + "] Không tồn tại!", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Something wrong here, Internal Server Error!",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
