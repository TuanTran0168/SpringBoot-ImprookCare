/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.controllers;

import com.tuantran.IMPROOK_CARE.Specifications.GenericSpecifications;
import com.tuantran.IMPROOK_CARE.dto.AddProfilePatientDTO;
import com.tuantran.IMPROOK_CARE.dto.UpdateProfilePatientDTO;
import com.tuantran.IMPROOK_CARE.models.ProfilePatient;
import com.tuantran.IMPROOK_CARE.models.User;
import com.tuantran.IMPROOK_CARE.service.ProfilePatientService;
import com.tuantran.IMPROOK_CARE.service.UserService;

import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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
public class ApiProfilePatientController {

    @Autowired
    private ProfilePatientService profilePatientService;

    @Autowired
    private UserService userService;

    @Autowired
    Environment environment;

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
    public ResponseEntity<String> updateProfilePatient(
            @Valid @RequestBody UpdateProfilePatientDTO updateProfilePatientDTO) {
        String message = "Có lỗi xảy ra!";
        int check = this.profilePatientService.updateProfilePatient(updateProfilePatientDTO);

        if (check == 1) {
            message = "Cập nhật hồ sơ bệnh nhân thành công!";
            return new ResponseEntity<>(message, HttpStatus.OK);
        }

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/auth/profile-patient/{profilePatientId}/")
    @CrossOrigin
    public ResponseEntity<ProfilePatient> profilePatientDetail(
            @PathVariable(value = "profilePatientId") String profilePatientId) {

        return new ResponseEntity<>(this.profilePatientService
                .findProfilePatientByProfilePatientIdAndActiveTrue(Integer.parseInt(profilePatientId)), HttpStatus.OK);
    }

    @GetMapping("/auth/user/{userId}/profile-patient/")
    @CrossOrigin
    public ResponseEntity<?> profilePatientByUserId(@PathVariable(value = "userId") String userId,
            @RequestParam Map<String, String> params) {
        String message = "Có lỗi xảy ra!";

        User user = this.userService.findUserByUserIdAndActiveTrue(Integer.parseInt(userId));
        if (user != null) {
            String pageNumber = params.get("pageNumber");
            String isLock = params.get("isLock");
            String profilePatientName = params.get("profilePatientName");
            String phonenumber = params.get("phonenumber");
            String gender = params.get("gender");

            List<Specification<ProfilePatient>> listSpec = new ArrayList<>();

            int defaultPageNumber = 0;
            Sort mySort = Sort.by("createdDate").descending();
            Pageable page = PageRequest.of(defaultPageNumber,
                    Integer.parseInt(this.environment.getProperty("spring.data.web.pageable.default-page-size")),
                    mySort);
            if (pageNumber != null && !pageNumber.isEmpty()) {
                if (!pageNumber.equals("NaN")) {
                    page = PageRequest.of(Integer.parseInt(pageNumber),
                            Integer.parseInt(
                                    this.environment.getProperty("spring.data.web.pageable.default-page-size")),
                            mySort);
                }
            }

            if (isLock != null && !isLock.isEmpty()) {
                Specification<ProfilePatient> spec = GenericSpecifications.fieldEquals("isLock",
                        Boolean.parseBoolean(isLock));
                listSpec.add(spec);
            }
            if (profilePatientName != null && !profilePatientName.isEmpty()) {
                Specification<ProfilePatient> spec = GenericSpecifications.fieldEquals("name", profilePatientName);
                listSpec.add(spec);
            }
            if (phonenumber != null && !phonenumber.isEmpty()) {
                Specification<ProfilePatient> spec = GenericSpecifications.fieldContains("phonenumber", phonenumber);
                listSpec.add(spec);
            }
            if (gender != null && !gender.isEmpty()) {
                Specification<ProfilePatient> spec = GenericSpecifications.fieldEquals("gender",
                        Boolean.parseBoolean(gender));
                listSpec.add(spec);
            }

            return new ResponseEntity<>(
                    this.profilePatientService
                            .findAllProfilePatientPageSpec(GenericSpecifications.createSpecification(listSpec), page),
                    HttpStatus.OK);
        } else {
            message = "User[" + userId + "] không tồn tại!";
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("/auth/soft-delete/profile-patient/{profilePatientId}/")
    @CrossOrigin
    public ResponseEntity<String> softDeleteProfilePatient(
            @PathVariable(value = "profilePatientId") String profilePatientId) {
        String message = "Có lỗi xảy ra!";

        int check = this.profilePatientService.softDeleteProfilePatient(Integer.parseInt(profilePatientId));

        if (check == 1) {
            message = "Xóa hồ sơ bệnh nhân thành công!";
            return new ResponseEntity<>(message, HttpStatus.NO_CONTENT);
        } else if (check == 2) {
            message = "Xóa hồ sơ bệnh nhân thất bại!";
        } else if (check == 3) {
            message = "Không tìm thấy hồ sơ bệnh nhân để xóa";
        }

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}
