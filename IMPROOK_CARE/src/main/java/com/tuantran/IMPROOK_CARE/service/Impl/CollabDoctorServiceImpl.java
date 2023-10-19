/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.service.Impl;

import com.tuantran.IMPROOK_CARE.Specifications.GenericSpecifications;
import com.tuantran.IMPROOK_CARE.dto.AddCollabDoctorDTO;
import com.tuantran.IMPROOK_CARE.models.CollabDoctor;
import com.tuantran.IMPROOK_CARE.models.CollabDoctorStatus;
import com.tuantran.IMPROOK_CARE.repository.CollabDoctorRepository;
import com.tuantran.IMPROOK_CARE.service.CollabDoctorService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 *
 * @author Administrator
 */
@Service
public class CollabDoctorServiceImpl implements CollabDoctorService {

    @Autowired
    private CollabDoctorRepository collabDoctorRepository;

    @Autowired
    private Environment environment;

    @Override
    public int addCollabDoctor(AddCollabDoctorDTO addCollabDoctorDTO) {
        try {
            CollabDoctor collabDoctor = new CollabDoctor();

            collabDoctor.setName(addCollabDoctorDTO.getName());
            collabDoctor.setPhonenumber(addCollabDoctorDTO.getPhonenumber());
            collabDoctor.setEmail(addCollabDoctorDTO.getEmail());

            collabDoctor.setStatusId(new CollabDoctorStatus(1));
            collabDoctor.setCreatedDate(new Date());

            this.collabDoctorRepository.save(collabDoctor);

            return 1;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    @Override
    public Page<CollabDoctor> findAllCollabDoctorPageSpec(Map<String, String> params) {

        String pageNumber = params.get("pageNumber");
        String name = params.get("name");
        String phonenumber = params.get("phonenumber");
        String email = params.get("email");

        List<Specification<CollabDoctor>> listSpec = new ArrayList<>();
        int defaultPageNumber = 0;
        Sort mySort = Sort.by("createdDate").descending();
        Pageable page = PageRequest.of(defaultPageNumber, Integer.parseInt(this.environment.getProperty("spring.data.web.pageable.default-page-size")), mySort);
        if (pageNumber != null && !pageNumber.isEmpty()) {
            if (!pageNumber.equals("NaN")) {
                page = PageRequest.of(Integer.parseInt(pageNumber), Integer.parseInt(this.environment.getProperty("spring.data.web.pageable.default-page-size")), mySort);
            }
        }

        if (name != null && !name.isEmpty()) {
            Specification<CollabDoctor> spec = GenericSpecifications.fieldContains("name", name);
            listSpec.add(spec);
        }

        if (phonenumber != null && !phonenumber.isEmpty()) {
            Specification<CollabDoctor> spec = GenericSpecifications.fieldContains("phonenumber", phonenumber);
            listSpec.add(spec);
        }

        if (email != null && !email.isEmpty()) {
            Specification<CollabDoctor> spec = GenericSpecifications.fieldContains("email", email);
            listSpec.add(spec);
        }

        return this.collabDoctorRepository.findAll(GenericSpecifications.createSpecification(listSpec), page);
    }

    @Override
    public int acceptCollabDoctor(int collabDoctorId) {
        Optional<CollabDoctor> collabDoctorOptional = this.collabDoctorRepository.findById(collabDoctorId);
        if (collabDoctorOptional.isPresent()) {
            CollabDoctor collabDoctor = collabDoctorOptional.get();
            collabDoctor.setStatusId(new CollabDoctorStatus(2));

            this.collabDoctorRepository.save(collabDoctor);
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public int denyCollabDoctor(int collabDoctorId) {
        Optional<CollabDoctor> collabDoctorOptional = this.collabDoctorRepository.findById(collabDoctorId);
        if (collabDoctorOptional.isPresent()) {
            CollabDoctor collabDoctor = collabDoctorOptional.get();
            collabDoctor.setStatusId(new CollabDoctorStatus(3));

            this.collabDoctorRepository.save(collabDoctor);
            return 1;
        } else {
            return 0;
        }
    }
}
