/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.repository;

import com.tuantran.IMPROOK_CARE.models.PrescriptionDetail;
import com.tuantran.IMPROOK_CARE.models.Prescriptions;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Administrator
 */
@Repository
@Transactional
public interface PrescriptionDetailRepository extends JpaRepository<PrescriptionDetail, Integer> {

    Page<PrescriptionDetail> findAll(Specification<PrescriptionDetail> createSpecification, Pageable page);
    
    Page<PrescriptionDetail> findAll(Specification<PrescriptionDetail> createSpecification);
    
    List<PrescriptionDetail> findPrescriptionDetailByPrescriptionId(Prescriptions prescriptionId);
}
