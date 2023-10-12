/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.service.Impl;

import com.tuantran.IMPROOK_CARE.Specifications.GenericSpecifications;
import com.tuantran.IMPROOK_CARE.models.PrescriptionDetail;
import com.tuantran.IMPROOK_CARE.models.Prescriptions;
import com.tuantran.IMPROOK_CARE.repository.PrescriptionDetailRepository;
import com.tuantran.IMPROOK_CARE.repository.PrescriptionRepository;
import com.tuantran.IMPROOK_CARE.service.PrescriptionDetailService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 *
 * @author Administrator
 */
@Service
public class PrescriptionDetailServiceImpl implements PrescriptionDetailService {

    @Autowired
    private PrescriptionDetailRepository prescriptionDetailRepository;

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Override
    public List<PrescriptionDetail> findPrescriptionDetailByPrescriptionId(int prescriptionId) {
//        Specification<Prescriptions> specPrescriptions = GenericSpecifications.fieldEquals("active", Boolean.TRUE);
        Optional<Prescriptions> prescriptionOptional = this.prescriptionRepository.findById(prescriptionId);

        if (prescriptionOptional.isPresent()) {
            return this.prescriptionDetailRepository.findPrescriptionDetailByPrescriptionId(prescriptionOptional.get());
        }

        return new ArrayList<>();
    }

}
