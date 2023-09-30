/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.controllers;

import com.tuantran.IMPROOK_CARE.components.email.MailService;
import com.tuantran.IMPROOK_CARE.dto.EmailDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
public class ApiEmailController {
    
    @Autowired
    private MailService mailService;

    @PostMapping("/public/send-email/")
    @CrossOrigin
    public ResponseEntity<EmailDTO> addBooking() {
        EmailDTO mail = new EmailDTO();
        mail.setMailFrom("2051050549tuan@ou.edu.vn");
        mail.setMailTo("2051050138hieu@ou.edu.vn");
        mail.setMailSubject("Chào bạn mình là Tuấn Trần");
        mail.setMailContent("DCMM!");
        this.mailService.sendEmail(mail);
        
        return new ResponseEntity<>(mail, HttpStatus.OK);
    }
}
