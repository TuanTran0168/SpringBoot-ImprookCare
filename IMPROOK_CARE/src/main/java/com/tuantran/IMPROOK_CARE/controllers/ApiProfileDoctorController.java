/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.controllers;

import com.tuantran.IMPROOK_CARE.dto.AddProfileDoctorDTO;
import com.tuantran.IMPROOK_CARE.dto.UpdateProfileDoctorDTO;
import com.tuantran.IMPROOK_CARE.models.ProfileDoctor;
import com.tuantran.IMPROOK_CARE.service.ProfileDoctorService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/public/profile-doctor/")
    @CrossOrigin
    public ResponseEntity<List<ProfileDoctor>> listProfileDoctor() {
        return new ResponseEntity<>(this.profileDoctorService.findAllProfileDoctorByActiveTrue(), HttpStatus.OK);
    }

    @GetMapping("/public/profile-doctor/{profileDoctorId}/")
    @CrossOrigin
    public ResponseEntity<ProfileDoctor> profileDoctorDetail(@PathVariable(value = "profileDoctorId") String profileDoctorId) {

        return new ResponseEntity<>(this.profileDoctorService.findProfileDoctorByProfileDoctorIdAndActiveTrue(Integer.parseInt(profileDoctorId)), HttpStatus.OK);
    }

    @GetMapping("/public/user/{userId}/profile-doctor/")
    @CrossOrigin
    public ResponseEntity<List<ProfileDoctor>> profileDoctorByUserId(@PathVariable(value = "userId") String userId) {

        return new ResponseEntity<>(this.profileDoctorService.findProfileDoctorByUserIdAndActiveTrue(Integer.parseInt(userId)), HttpStatus.OK);
    }

    @GetMapping("/public/search-profile-doctors/")
    @CrossOrigin
    public ResponseEntity<?> listSearchUser(@RequestParam Map<String, String> params) {
        return new ResponseEntity<>(this.profileDoctorService.findAllProfileDoctorPageSpec(params), HttpStatus.OK);
    }

    @DeleteMapping("/auth/doctor/soft-delete/profile-doctor/{profileDoctorId}/")
    @CrossOrigin
    public ResponseEntity<String> softDeleteProfileDoctor(@PathVariable(value = "profileDoctorId") String profileDoctorId) {
        String message = "Có lỗi xảy ra!";

        int check = this.profileDoctorService.softDeleteProfileDoctor(Integer.parseInt(profileDoctorId));

        if (check == 1) {
            message = "Xóa hồ sơ bác sĩ thành công!";
            return new ResponseEntity<>(message, HttpStatus.NO_CONTENT);
        } else if (check == 2) {
            message = "Xóa hồ sơ bác sĩ thất bại!";
        }
        else if (check == 3) {
            message = "Không tìm thấy hồ sơ bác sĩ để xóa";
        }

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}
