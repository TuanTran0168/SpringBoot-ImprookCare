/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.service;

import com.tuantran.IMPROOK_CARE.dto.AddCollabDoctorDTO;
import com.tuantran.IMPROOK_CARE.models.CollabDoctor;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

/**
 *
 * @author Administrator
 */
public interface CollabDoctorService {
    int addCollabDoctor(AddCollabDoctorDTO addCollabDoctorDTO);
    Page<CollabDoctor> findAllCollabDoctorPageSpec(Map<String, String> params);
    int acceptCollabDoctor(int collabDoctorId);
    int denyCollabDoctor(int collabDoctorId);
}
