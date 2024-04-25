/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.service.Impl;

import com.tuantran.IMPROOK_CARE.models.TestService;
import com.tuantran.IMPROOK_CARE.repository.TestServiceRepository;
import com.tuantran.IMPROOK_CARE.service.TestServiceService;

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
public class TestServiceServiceImpl implements TestServiceService {
    @Autowired
    private TestServiceRepository testServiceRepository;

    @Override
    public Optional<TestService> findByTestServiceId(Integer testServiceId) {
        return this.testServiceRepository.findByTestServiceId(testServiceId);
    }

    @Override
    public Page<?> findAll(Specification<?> createSpecification, Pageable page) {
        return this.testServiceRepository.findAll(createSpecification, page);
    }

}
