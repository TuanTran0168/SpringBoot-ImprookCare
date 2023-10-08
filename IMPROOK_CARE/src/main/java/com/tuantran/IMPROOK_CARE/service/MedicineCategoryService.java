/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.service;

import com.tuantran.IMPROOK_CARE.models.MedicineCategory;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Administrator
 */
public interface MedicineCategoryService {

    List<MedicineCategory> findMedicineCategoryByActiveTrue();

    MedicineCategory findMedicineCategoryByCategoryIdAndActiveTrue(int medicineCategoryId);
}
