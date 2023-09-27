/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.controllers;

import com.tuantran.IMPROOK_CARE.dto.AddProfileDoctorDTO;
import com.tuantran.IMPROOK_CARE.dto.UpdateProfileDoctorDTO;
import com.tuantran.IMPROOK_CARE.service.ProfileDoctorService;
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
public class ApiProfileDoctorController {

    @Autowired
    private ProfileDoctorService profileDoctorService;

    @PostMapping("/auth/doctor/add-profile-doctor/")
    @CrossOrigin
    public ResponseEntity<String> addProfileDoctor(@Valid @RequestBody AddProfileDoctorDTO addProfileDoctorDTO) {
        String message = "Có lỗi xảy ra!";
        int check = this.profileDoctorService.addProfileDoctor(addProfileDoctorDTO);

        if (check == 1) {
            message = "Tạo hồ sơ bác sĩ thành công!";
            return new ResponseEntity<>(message, HttpStatus.OK);
        }

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/auth/doctor/update-profile-doctor/")
    @CrossOrigin
    public ResponseEntity<String> updateProfileDoctor(@Valid @RequestBody UpdateProfileDoctorDTO updateProfileDoctorDTO) {
        String message = "Có lỗi xảy ra!";
        int check = this.profileDoctorService.updateProfileDoctor(updateProfileDoctorDTO);

        if (check == 1) {
            message = "Cập nhật hồ sơ bác sĩ thành công!";
            return new ResponseEntity<>(message, HttpStatus.OK);
        }

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}
