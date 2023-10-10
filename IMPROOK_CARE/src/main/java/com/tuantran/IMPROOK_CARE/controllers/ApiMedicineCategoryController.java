/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.controllers;

import com.tuantran.IMPROOK_CARE.dto.AddMedicineCategoryDTO;
import com.tuantran.IMPROOK_CARE.dto.UpdateMedicineCategoryDTO;
import com.tuantran.IMPROOK_CARE.models.MedicineCategory;
import com.tuantran.IMPROOK_CARE.service.MedicineCategoryService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping("/auth/admin/add-medicine-category/")
    @CrossOrigin
    public ResponseEntity<String> addMedicineCategory(@Valid @RequestBody AddMedicineCategoryDTO addMedicineCategoryDTO) throws Exception {
        String message = "Có lỗi xảy ra!";
        int check = this.medicineCategoryService.addMedicineCategory(addMedicineCategoryDTO);

        if (check == 1) {
            message = "Thêm danh mục thuốc thành công!";
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else if (check == 0) {
            message = "Thêm danh mục thuốc thất bại!";
        }

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/auth/admin/update-medicine-category/")
    @CrossOrigin
    public ResponseEntity<String> updateMedicineCategory(@Valid @RequestBody UpdateMedicineCategoryDTO updateMedicineCategoryDTO) {

        String message = "Có lỗi xảy ra!";
        int check = this.medicineCategoryService.updateMedicineCategory(updateMedicineCategoryDTO);

        if (check == 1) {
            message = "Cập nhật danh mục thuốc thành công!";
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else if (check == 2) {
            message = "Danh mục thuốc không tồn tại!";
        } else if (check == 0) {
            message = "Cập nhật danh mục thuốc thất bại!";
        }

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/public/search-medicine-categories/")
    @CrossOrigin
    public ResponseEntity<?> listSearchMedicineCategories(@RequestParam Map<String, String> params) {
        return new ResponseEntity<>(this.medicineCategoryService.findAllMedicineCategoryPageSpec(params), HttpStatus.OK);
    }
}
