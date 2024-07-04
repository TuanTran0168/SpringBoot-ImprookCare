/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.service.Impl;

import com.tuantran.IMPROOK_CARE.models.TestResult;
import com.tuantran.IMPROOK_CARE.repository.TestResultRepository;
import com.tuantran.IMPROOK_CARE.service.TestResultService;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 *
 * @author Administrator
 */
@Service
public class TestResultServiceImpl implements TestResultService {
    @Autowired
    private TestResultRepository testResultRepository;

    @Override
    public Optional<TestResult> findByTestResultIdAndActiveTrue(Integer testResultId) {
        return this.testResultRepository.findByTestResultIdAndActiveTrue(testResultId);
    }

    @Override
    public TestResult addTestResult(TestResult testResult) {
        return this.testResultRepository.save(testResult);
    }

    @Override
    public TestResult updateTestResult(TestResult testResult) {
        return this.testResultRepository.save(testResult);
    }

    @Override
    public Page<?> findAllTestResultPageSpec(Specification<?> createSpecification, Pageable page) {
        return this.testResultRepository.findAll(createSpecification, page);
    }

}
