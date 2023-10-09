/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.service;

import com.tuantran.IMPROOK_CARE.dto.AddMedicineDTO;
import com.tuantran.IMPROOK_CARE.dto.UpdateMedicineDTO;
import com.tuantran.IMPROOK_CARE.models.Medicine;
import com.tuantran.IMPROOK_CARE.models.MedicineCategory;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Administrator
 */
public interface MedicineService {

    List<Medicine> findMedicineByActiveTrue();

    Medicine findMedicineByMedicineIdAndActiveTrue(int medicineId);

    List<Medicine> findMedicineByCategoryId(int medicineCategoryId);

    int addMedicine(AddMedicineDTO addMedicineDTO, MultipartFile avatar);

    int updateMedicine(UpdateMedicineDTO updateMedicineDTO, MultipartFile avatar);
    
    public List<Medicine> findAllMedicinePageSpec(Map<String, String> params);
}
