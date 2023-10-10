/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.service;

import com.tuantran.IMPROOK_CARE.dto.AddMedicineCategoryDTO;
import com.tuantran.IMPROOK_CARE.dto.UpdateMedicineCategoryDTO;
import com.tuantran.IMPROOK_CARE.models.MedicineCategory;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Administrator
 */
public interface MedicineCategoryService {

    List<MedicineCategory> findMedicineCategoryByActiveTrue();

    MedicineCategory findMedicineCategoryByCategoryIdAndActiveTrue(int medicineCategoryId);

    int addMedicineCategory(AddMedicineCategoryDTO addMedicineCategoryDTO);

    int updateMedicineCategory(UpdateMedicineCategoryDTO updateMedicineCategoryDTO);
    
    Page<MedicineCategory> findAllMedicineCategoryPageSpec(Map<String, String> params);
}
