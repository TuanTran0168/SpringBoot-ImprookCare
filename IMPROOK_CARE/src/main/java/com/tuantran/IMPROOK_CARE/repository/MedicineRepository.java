/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.repository;

import com.tuantran.IMPROOK_CARE.models.Medicine;
import com.tuantran.IMPROOK_CARE.models.MedicineCategory;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
public interface MedicineRepository extends JpaRepository<Medicine, Integer> {

    List<Medicine> findMedicineByActiveTrue();

    Optional<Medicine> findMedicineByMedicineIdAndActiveTrue(int medicineId);

    List<Medicine> findMedicineByCategoryId(MedicineCategory medicineCategoryId);

    Page<Medicine> findAll(Specification<Medicine> createSpecification, Pageable page);

    List<Medicine> findAll(Specification<Medicine> createSpecification, Sort sort);
}
