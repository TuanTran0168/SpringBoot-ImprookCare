/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.controllers;

import com.tuantran.IMPROOK_CARE.components.twilio.SmsService;
import com.tuantran.IMPROOK_CARE.configs.twilio.TwilioConfiguration;
import com.tuantran.IMPROOK_CARE.configs.twilio.TwilioVerification;
import com.tuantran.IMPROOK_CARE.dto.AuthMessageTwilioDTO;
import com.tuantran.IMPROOK_CARE.dto.SmsRequest;
import com.twilio.Twilio;
import com.twilio.exception.ApiException;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Administrator
 */
@RestController
@RequestMapping("/api")
public class ApiTwilioController {

    @Autowired
    private SmsService smsService;

    @Autowired
    TwilioConfiguration twilioConfiguration;
    
    @Autowired
    TwilioVerification twilioVerification;

    @PostMapping("/sendSMS/")
    public void sendSms(@Valid @RequestBody SmsRequest smsRequest) {
        smsService.sendSms(smsRequest);
    }

    @GetMapping("/verification/")
    public ResponseEntity<String> verification(@RequestBody Map<String, String> params) {
        String message = "Có lỗi xảy ra!";
        String phonenumber = params.get("phonenumber");
        int check = this.twilioVerification.verification(phonenumber);
        
        if (check == 1) {
            message = "Gửi tin nhắn thành công đến số điện thoại: " + phonenumber;
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        else if (check == 2) {
            message = "Số điện thoại không hợp lệ!";
        }
        
        return new ResponseEntity<>(message,  HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/verification-check/")
    public ResponseEntity<String> verification_check(@Valid @RequestBody AuthMessageTwilioDTO authMessageTwilioDTO) {
        String message = "Có lỗi xảy ra!";
        int check = this.twilioVerification.verification_check(authMessageTwilioDTO.getCode(), authMessageTwilioDTO.getPhoneNumber());
        
        if (check == 1) {
            message = "Xác thực thành công!";
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        else if (check == 2) {
            message = "Mã xác thực không chính xác!";
        }
        else if (check == 3) {
            message = "Mã xác thực đã hết hạn! Vui lòng gửi lại mã xác thực khác!";
        }
        
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}
