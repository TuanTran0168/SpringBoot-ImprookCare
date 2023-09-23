/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.controllers;

import com.tuantran.IMPROOK_CARE.dto.AddProfilePatientDTO;
import com.tuantran.IMPROOK_CARE.dto.UpdateProfilePatientDTO;
import com.tuantran.IMPROOK_CARE.service.ProfilePatientService;
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
public class ApiProfilePatientController {
    
    @Autowired
    private ProfilePatientService profilePatientService;
    
    @PostMapping("/auth/add-profile-patient/")
    @CrossOrigin
    public ResponseEntity<String> addProfilePatient(@Valid @RequestBody AddProfilePatientDTO addProfilePatientDTO) {
        String message = "Có lỗi xảy ra!";
        int check = this.profilePatientService.addProfilePatient(addProfilePatientDTO);
        
        if (check == 1) {
            message = "Tạo hồ sơ bệnh nhân thành công!";
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
    
    @PostMapping("/auth/update-profile-patient/")
    @CrossOrigin
    public ResponseEntity<String> updateProfilePatient(@Valid @RequestBody UpdateProfilePatientDTO updateProfilePatientDTO) {
        String message = "Có lỗi xảy ra!";
        int check = this.profilePatientService.updateProfilePatient(updateProfilePatientDTO);
        
        if (check == 1) {
            message = "Cập nhật hồ sơ bệnh nhân thành công!";
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}
