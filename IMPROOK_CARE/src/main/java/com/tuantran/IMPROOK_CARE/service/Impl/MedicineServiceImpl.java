/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.service.Impl;

import com.tuantran.IMPROOK_CARE.models.Medicine;
import com.tuantran.IMPROOK_CARE.models.MedicineCategory;
import com.tuantran.IMPROOK_CARE.repository.MedicineCategoryRepository;
import com.tuantran.IMPROOK_CARE.repository.MedicineRepository;
import com.tuantran.IMPROOK_CARE.service.MedicineService;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Administrator
 */
@Repository
public class MedicineServiceImpl implements MedicineService {

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private MedicineCategoryRepository medicineCategoryRepository;

    @Override
    public List<Medicine> findMedicineByActiveTrue() {
        return this.medicineRepository.findMedicineByActiveTrue();
    }

    @Override
    public Medicine findMedicineByMedicineIdAndActiveTrue(int medicineId) {

        try {
            Optional<Medicine> medicineOptional = this.medicineRepository.findMedicineByMedicineIdAndActiveTrue(medicineId);
            if (medicineOptional.isPresent()) {
                return medicineOptional.get();
            } else {
                return null;
            }
        } catch (NoSuchElementException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Medicine> findMedicineByCategoryId(int medicineCategoryId) {
        try {
            Optional<MedicineCategory> medicineCategoryOptional = this.medicineCategoryRepository.findMedicineCategoryByCategoryIdAndActiveTrue(medicineCategoryId);
            if (medicineCategoryOptional.isPresent()) {
                return this.medicineRepository.findMedicineByCategoryId(medicineCategoryOptional.get());
            } else {
                return new ArrayList<>();
            }
        } catch (NoSuchElementException ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        }

    }

}
