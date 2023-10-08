/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.service.Impl;

import com.tuantran.IMPROOK_CARE.dto.AddMedicineCategoryDTO;
import com.tuantran.IMPROOK_CARE.dto.UpdateMedicineCategoryDTO;
import com.tuantran.IMPROOK_CARE.models.MedicineCategory;
import com.tuantran.IMPROOK_CARE.repository.MedicineCategoryRepository;
import com.tuantran.IMPROOK_CARE.service.MedicineCategoryService;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Administrator
 */
@Service
public class MedicineCategoryServiceImpl implements MedicineCategoryService {
    
    @Autowired
    private MedicineCategoryRepository medicineCategoryRepository;
    
    @Override
    public List<MedicineCategory> findMedicineCategoryByActiveTrue() {
        return this.medicineCategoryRepository.findMedicineCategoryByActiveTrue();
    }
    
    @Override
    public MedicineCategory findMedicineCategoryByCategoryIdAndActiveTrue(int medicineCategoryId) {
        try {
            Optional<MedicineCategory> medicineCategoryOptional = this.medicineCategoryRepository.findMedicineCategoryByCategoryIdAndActiveTrue(medicineCategoryId);
            if (medicineCategoryOptional.isPresent()) {
                return this.medicineCategoryRepository.findMedicineCategoryByCategoryIdAndActiveTrue(medicineCategoryId).get();
            } else {
                return null;
            }
        } catch (NoSuchElementException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    @Override
    public int addMedicineCategory(AddMedicineCategoryDTO addMedicineCategoryDTO) {
        try {
            MedicineCategory medicineCategory = new MedicineCategory();
            medicineCategory.setCategoryName(addMedicineCategoryDTO.getMedicineCategoryName());
            medicineCategory.setActive(Boolean.TRUE);
            medicineCategory.setCreatedDate(new Date());
            this.medicineCategoryRepository.save(medicineCategory);
            return 1;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }
    
    @Override
    public int updateMedicineCategory(UpdateMedicineCategoryDTO updateMedicineCategoryDTO) {
        try {
            Optional<MedicineCategory> medicineCategoryOptional = this.medicineCategoryRepository.findMedicineCategoryByCategoryIdAndActiveTrue(Integer.parseInt(updateMedicineCategoryDTO.getMedicineCategoryId()));
            if (medicineCategoryOptional.isPresent()) {
                MedicineCategory medicineCategory = medicineCategoryOptional.get();
                medicineCategory.setCategoryName(updateMedicineCategoryDTO.getMedicineCategoryName());
                medicineCategory.setUpdatedDate(new Date());
                
                this.medicineCategoryRepository.save(medicineCategory);
                return 1;
            } else {
                return 2; // Không tìm thấy gì để update
            }
        } catch (NoSuchElementException ex) {
            ex.printStackTrace();
            return 0;
        } catch (DataAccessException ex) {
            Logger.getLogger(MedicineCategoryServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }
    
}
