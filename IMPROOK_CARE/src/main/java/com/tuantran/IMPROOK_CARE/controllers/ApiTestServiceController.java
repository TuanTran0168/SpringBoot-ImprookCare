/*
* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt
to change this license
* Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit
this template
*/
package com.tuantran.IMPROOK_CARE.controllers;

import com.tuantran.IMPROOK_CARE.Specifications.GenericSpecifications;
import com.tuantran.IMPROOK_CARE.models.TestService;
import com.tuantran.IMPROOK_CARE.service.TestServiceService;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Administrator
 */
@RestController
@RequestMapping("/api")
public class ApiTestServiceController {

    @Autowired
    private TestServiceService testServiceService;

    @Autowired
    Environment environment;

    @GetMapping("/public/search-test-service/")
    @CrossOrigin
    public ResponseEntity<?> profilePatientByUserId(@RequestParam Map<String, String> params) {

        String pageNumber = params.get("pageNumber");
        String testServiceId = params.get("testServiceId");
        String testServiceName = params.get("testServiceName");

        List<Specification<TestService>> listSpec = new ArrayList<>();

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
            Specification<TestService> spec = GenericSpecifications.fieldEquals("testServiceId",
                    testServiceId);
            listSpec.add(spec);
        }

        if (testServiceName != null && !testServiceName.isEmpty()) {
            Specification<TestService> spec = GenericSpecifications.fieldContains("testServiceName", testServiceName);
            listSpec.add(spec);
        }

        return new ResponseEntity<>(
                this.testServiceService.findAllTestServicePageSpec(GenericSpecifications.createSpecification(listSpec),
                        page),
                HttpStatus.OK);

    }
}
