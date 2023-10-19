/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.controllers;

import com.tuantran.IMPROOK_CARE.dto.AddCollabDoctorDTO;
import com.tuantran.IMPROOK_CARE.service.CollabDoctorService;
import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Administrator
 */
@RestController
@RequestMapping("/api")
public class ApiCollabDoctorController {

    @Autowired
    private CollabDoctorService collabDoctorService;

    @PostMapping("/public/add-collab-doctor/")
    @CrossOrigin
    public ResponseEntity<?> addCollabDoctor(@Valid @RequestBody AddCollabDoctorDTO addCollabDoctorDTO) {
        String message = "Có lỗi xảy ra!";
        int check = this.collabDoctorService.addCollabDoctor(addCollabDoctorDTO);

        if (check == 1) {
            message = "Đã đăng ký hợp tác bác sĩ, hãy chờ phản hồi!";
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else if (check == 0) {
            message = "Đăng ký hợp tác thất bại!";
        }

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/auth/search-collab-doctor/")
    @CrossOrigin
    public ResponseEntity<?> findCollabDoctorPageSpec(@RequestParam Map<String, String> params) {
        return new ResponseEntity<>(this.collabDoctorService.findAllCollabDoctorPageSpec(params), HttpStatus.OK);
    }

    @PostMapping("/auth/accept-collab-doctor/")
    @CrossOrigin
    public ResponseEntity<?> acceptCollabDoctor(@RequestBody String collabDoctorId) {
        String message = "Có lỗi xảy ra!";
        int check = this.collabDoctorService.acceptCollabDoctor(Integer.parseInt(collabDoctorId));

        if (check == 1) {
            message = "Xác nhận thành công hợp tác!";
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else if (check == 0) {
            message = "Xác nhận thất bại hợp tác!";
        }

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/auth/deny-collab-doctor/")
    @CrossOrigin
    public ResponseEntity<?> denyCollabDoctor(@RequestBody String collabDoctorId) {
        String message = "Có lỗi xảy ra!";
        int check = this.collabDoctorService.denyCollabDoctor(Integer.parseInt(collabDoctorId));

        if (check == 1) {
            message = "Từ chối thành công hợp tác!";
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else if (check == 0) {
            message = "Từ chối thất bại hợp tác!";
        }

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}
