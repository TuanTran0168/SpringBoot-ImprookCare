/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.repository;

import com.tuantran.IMPROOK_CARE.models.MedicineCategory;
import java.util.List;
import java.util.Optional;
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
public interface MedicineCategoryRepository extends JpaRepository<MedicineCategory, Integer> {

    List<MedicineCategory> findMedicineCategoryByActiveTrue();

    Optional<MedicineCategory> findMedicineCategoryByCategoryIdAndActiveTrue(int medicineCategoryId);
    
    Page<MedicineCategory> findAll(Specification<MedicineCategory> createSpecification, Pageable page);
}
