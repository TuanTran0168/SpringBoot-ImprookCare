/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.tuantran.IMPROOK_CARE.models.TestResult;

/**
 *
 * @author Administrator
 */
public interface TestResultService {
        Optional<TestResult> findByTestResultIdAndActiveTrue(Integer testResultId);

        TestResult addTestResult(TestResult testResult);

        TestResult updateTestResult(TestResult testResult);

        Page<?> findAll(Specification<?> createSpecification, Pageable page);
}
