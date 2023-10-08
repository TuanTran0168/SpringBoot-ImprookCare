/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.controllers;

import com.tuantran.IMPROOK_CARE.models.MedicineCategory;
import com.tuantran.IMPROOK_CARE.service.MedicineCategoryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Administrator
 */
@RestController
@RequestMapping("/api")
public class ApiMedicineCategoryController {

    @Autowired
    private MedicineCategoryService medicineCategoryService;

    @GetMapping("/public/medicine-categories/")
    @CrossOrigin
    public ResponseEntity<List<MedicineCategory>> listMedicineCategories() {
        return ResponseEntity.ok().body(this.medicineCategoryService.findMedicineCategoryByActiveTrue());
    }

    @GetMapping("/public/medicine-category/{categoryId}/")
    @CrossOrigin
    public ResponseEntity<MedicineCategory> medicineCategories(@PathVariable(value = "categoryId") String categoryId) {
        return new ResponseEntity<>(this.medicineCategoryService.findMedicineCategoryByCategoryIdAndActiveTrue(Integer.parseInt(categoryId)), HttpStatus.OK);
    }
}
