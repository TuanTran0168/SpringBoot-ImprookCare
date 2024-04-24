/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.controllers;

import com.tuantran.IMPROOK_CARE.dto.AddTestResultDTO;
import com.tuantran.IMPROOK_CARE.dto.UpdateTestResultDTO;
import com.tuantran.IMPROOK_CARE.models.Booking;
import com.tuantran.IMPROOK_CARE.models.TestResult;
import com.tuantran.IMPROOK_CARE.models.TestService;
import com.tuantran.IMPROOK_CARE.models.User;
import com.tuantran.IMPROOK_CARE.service.BookingService;
import com.tuantran.IMPROOK_CARE.service.TestResultService;
import com.tuantran.IMPROOK_CARE.service.TestServiceService;
import com.tuantran.IMPROOK_CARE.service.UserService;

import jakarta.validation.Valid;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
public class ApiTestResultController {

    @Autowired
    private TestResultService testResultService;

    @Autowired
    private TestServiceService testServiceService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private UserService userService;

    /*
     * 2 API này sau này sẽ suy nghĩ tới việc lưu hình ảnh
     */
    @PostMapping("/auth/nurse/add-test-result/")
    @PreAuthorize("hasRole('NURSE')")
    @CrossOrigin
    public ResponseEntity<?> addTestResult(@Valid @RequestBody AddTestResultDTO addTestResultDTO) {
        String message = "Có lỗi xảy ra!";

        Optional<Booking> bookingOptional = this.bookingService
                .findBookingByBookingIdAndActiveTrue(Integer.parseInt(addTestResultDTO.getBookingId()));
        Optional<TestService> testServiceOptional = this.testServiceService
                .findByTestServiceId(Integer.parseInt(addTestResultDTO.getTestServiceId()));

        if (bookingOptional.isPresent() && testServiceOptional.isPresent()) {
            TestResult testResult = new TestResult();
            testResult.setBookingId(bookingOptional.get());
            testResult.setTestServiceId(testServiceOptional.get());

            return new ResponseEntity<>(this.testResultService.addTestResult(testResult), HttpStatus.CREATED);
        } else {
            message = "Booking[" + addTestResultDTO.getBookingId() + "] hoặc TestService[ "
                    + addTestResultDTO.getTestServiceId() + "] không tồn tại!";
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/auth/nurse/update-test-result/")
    @PreAuthorize("hasRole('NURSE')")
    @CrossOrigin
    public ResponseEntity<?> updateTestResult(@Valid @RequestBody UpdateTestResultDTO updateTestResultDTO) {
        String message = "Có lỗi xảy ra!";
        Optional<TestResult> testResultOptional = this.testResultService
                .findByTestResultIdAndActiveTrue(Integer.parseInt(updateTestResultDTO.getTestResultId()));
        User user = this.userService.findUserByUserIdAndActiveTrue(Integer.parseInt(updateTestResultDTO.getUserId()));

        if (testResultOptional.isPresent() && user != null) {
            TestResult testResult = testResultOptional.get();
            testResult.setTestResultDiagnosis(updateTestResultDTO.getTestResultDiagnosis());
            testResult.setTestResultValue(updateTestResultDTO.getTestResultValue());
            testResult.setUserId(user);

            return new ResponseEntity<>(this.testResultService.updateTestResult(testResult), HttpStatus.OK);
        } else {
            message = "TestResult[" + updateTestResultDTO.getTestResultId() + "] hoặc User[ "
                    + updateTestResultDTO.getUserId() + "] không tồn tại!";
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }
}
