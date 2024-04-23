/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.controllers;

import com.tuantran.IMPROOK_CARE.models.ProfilePatient;
import com.tuantran.IMPROOK_CARE.service.PaymentHistoryService;
import com.tuantran.IMPROOK_CARE.service.ProfilePatientService;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Administrator
 */
@RestController
@RequestMapping("/api")
public class ApiPaymentHistoryController {

    @Autowired
    private PaymentHistoryService historyService;

    @Autowired
    private Environment environment;

    @Autowired
    private ProfilePatientService profilePatientService;

    @GetMapping("/auth/profile-patient/{profilePatientId}/payment-history/")
    @CrossOrigin
    public ResponseEntity<?> findPaymentHistoryByProfilePatientId(
            @PathVariable(value = "profilePatientId") String profilePatientId,
            @RequestParam Map<String, String> params) {
        String message = "Có lỗi xảy ra!";

        Optional<ProfilePatient> profilePatientOptional = this.profilePatientService
                .findProfilePatientByProfilePatientIdAndActiveTrueOptional(Integer.parseInt(profilePatientId));
        if (profilePatientOptional.isPresent()) {
            String pageNumber = params.get("pageNumber");
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
            return ResponseEntity.ok()
                    .body(this.historyService.findPaymentHistoryByProfilePatientId(
                            profilePatientOptional.get().getProfilePatientId(), page));
        } else {
            message = "ProfilePatient[" + profilePatientId + "] không tồn tại!";
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }

    }

}
