/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.controllers;

import com.tuantran.IMPROOK_CARE.Specifications.GenericSpecifications;
import com.tuantran.IMPROOK_CARE.dto.AddTestResultDTO;
import com.tuantran.IMPROOK_CARE.dto.ReturnTestResultDTO;
import com.tuantran.IMPROOK_CARE.dto.UpdateTestResultDTO;
import com.tuantran.IMPROOK_CARE.models.Booking;
import com.tuantran.IMPROOK_CARE.models.ProfilePatient;
import com.tuantran.IMPROOK_CARE.models.TestResult;
import com.tuantran.IMPROOK_CARE.models.TestService;
import com.tuantran.IMPROOK_CARE.models.User;
import com.tuantran.IMPROOK_CARE.service.BookingService;
import com.tuantran.IMPROOK_CARE.service.TestResultService;
import com.tuantran.IMPROOK_CARE.service.TestServiceService;
import com.tuantran.IMPROOK_CARE.service.UserService;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
public class ApiTestResultController {

    @Autowired
    private TestResultService testResultService;

    @Autowired
    private TestServiceService testServiceService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private UserService userService;

    @Autowired
    Environment environment;

    /*
     * 2 API này sau này sẽ suy nghĩ tới việc lưu hình ảnh
     */
    @PostMapping("/auth/doctor/add-test-result/")
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

    @PostMapping("/auth/nurse/return-test-result/")
    @PreAuthorize("hasRole('NURSE')")
    @CrossOrigin
    public ResponseEntity<?> returnTestResult(@Valid @RequestBody ReturnTestResultDTO returnTestResultDTO) {
        String message = "Có lỗi xảy ra!";
        Optional<TestResult> testResultOptional = this.testResultService
                .findByTestResultIdAndActiveTrue(Integer.parseInt(returnTestResultDTO.getTestResultId()));
        User user = this.userService.findUserByUserIdAndActiveTrue(Integer.parseInt(returnTestResultDTO.getUserId()));

        if (testResultOptional.isPresent() && user != null) {
            TestResult testResult = testResultOptional.get();
            testResult.setTestResultDiagnosis(returnTestResultDTO.getTestResultDiagnosis());
            testResult.setTestResultValue(returnTestResultDTO.getTestResultValue());
            testResult.setUserId(user);

            return new ResponseEntity<>(this.testResultService.updateTestResult(testResult), HttpStatus.OK);
        } else {
            message = "TestResult[" + returnTestResultDTO.getTestResultId() + "] hoặc User[ "
                    + returnTestResultDTO.getUserId() + "] không tồn tại!";
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/auth/doctor/update-test-result/")
    @CrossOrigin
    public ResponseEntity<?> updateTestResult(@Valid @RequestBody UpdateTestResultDTO updateTestResultDTO) {
        String message = "Có lỗi xảy ra!";
        Optional<TestResult> testResultOptional = this.testResultService
                .findByTestResultIdAndActiveTrue(Integer.parseInt(updateTestResultDTO.getTestResultId()));

        if (testResultOptional.isPresent()) {
            TestResult testResult = testResultOptional.get();
            Optional<TestService> testServiceOptional = this.testServiceService
                    .findByTestServiceId(Integer.parseInt(updateTestResultDTO.getTestServiceId()));
            if (testServiceOptional.isPresent()) {
                testResult.setTestServiceId(testServiceOptional.get());
            }

            return new ResponseEntity<>(this.testResultService.updateTestResult(testResult), HttpStatus.OK);
        } else {
            message = "TestResult[" + updateTestResultDTO.getTestResultId() + "] không tồn tại!";
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/auth/search-test-result/")
    @CrossOrigin
    public ResponseEntity<?> profilePatientByUserId(@RequestParam Map<String, String> params) {

        String pageNumber = params.get("pageNumber");
        String testServiceId = params.get("testServiceId");
        String testServiceName = params.get("testServiceName");
        String userId = params.get("userId");
        String testResultValue = params.get("testResultValue");
        String bookingId = params.get("bookingId");
        String profilePatientName = params.get("profilePatientName");
        String profilePatientId = params.get("profilePatientId");

        List<Specification<TestResult>> listSpec = new ArrayList<>();

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

        if (testServiceId != null && !testServiceId.isEmpty()) {
            Optional<TestService> testServiceOptional = this.testServiceService
                    .findByTestServiceId(Integer.parseInt(testServiceId));

            if (testServiceOptional.isPresent()) {
                Specification<TestResult> spec = GenericSpecifications.fieldEquals("testServiceId",
                        testServiceOptional.get());
                listSpec.add(spec);
            }
        }

        if (testServiceName != null && !testServiceName.isEmpty()) {
            Specification<TestResult> spec = GenericSpecifications.fieldContains("testServiceName", testServiceName);
            listSpec.add(spec);
        }
        if (userId != null && !userId.isEmpty()) {
            User user = this.userService.findUserByUserIdAndActiveTrue(Integer.parseInt(userId));
            if (user != null) {
                Specification<TestResult> spec = GenericSpecifications.fieldEquals("userId", user);
                listSpec.add(spec);
            }
        }
        if (testResultValue != null && !testResultValue.isEmpty()) {
            Specification<TestResult> spec = GenericSpecifications.fieldContains("testResultValue", testResultValue);
            listSpec.add(spec);
        }

        if (bookingId != null && !bookingId.isEmpty()) {
            Optional<Booking> bookingOptional = this.bookingService
                    .findBookingByBookingIdAndActiveTrue(Integer.parseInt(bookingId));

            if (bookingOptional.isPresent()) {
                Specification<TestResult> spec = GenericSpecifications.fieldEquals("bookingId",
                        bookingOptional.get());
                listSpec.add(spec);
            }
        }

        if (profilePatientName != null && !profilePatientName.isEmpty()) {
            Specification<TestResult> specificationProMax = (root, query, criteriaBuilder) -> {
                Join<TestResult, Booking> bookingJoin = root.join("bookingId");
                Join<Booking, ProfilePatient> profilePatientJoin = bookingJoin.join("profilePatientId");
                Predicate profilePatientNamePredicate = criteriaBuilder.equal(profilePatientJoin.get("name"),
                        profilePatientName);
                return criteriaBuilder.and(profilePatientNamePredicate);
            };

            listSpec.add(specificationProMax);
        }

        if (profilePatientId != null && !profilePatientId.isEmpty()) {
            Specification<TestResult> specificationProMax = (root, query, criteriaBuilder) -> {
                Join<TestResult, Booking> bookingJoin = root.join("bookingId");
                Join<Booking, ProfilePatient> profilePatientJoin = bookingJoin.join("profilePatientId");
                Predicate profilePatientIdPredicate = criteriaBuilder.equal(
                        profilePatientJoin.get("profilePatientId"),
                        profilePatientId);
                return criteriaBuilder.and(profilePatientIdPredicate);
            };

            listSpec.add(specificationProMax);
        }

        return new ResponseEntity<>(
                this.testResultService.findAllTestResultPageSpec(GenericSpecifications.createSpecification(listSpec),
                        page),
                HttpStatus.OK);

    }
}
