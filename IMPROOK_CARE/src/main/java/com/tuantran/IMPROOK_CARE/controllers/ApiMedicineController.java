/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.controllers;

import com.tuantran.IMPROOK_CARE.dto.AddMedicineDTO;
import com.tuantran.IMPROOK_CARE.dto.UpdateMedicineDTO;
import com.tuantran.IMPROOK_CARE.models.Medicine;
import com.tuantran.IMPROOK_CARE.models.MedicineCategory;
import com.tuantran.IMPROOK_CARE.service.MedicineService;
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
public class ApiMedicineController {

    @Autowired
    private MedicineService medicineService;

    @GetMapping("/public/medicines/")
    @CrossOrigin
    public ResponseEntity<List<Medicine>> listMedicine() {
        return ResponseEntity.ok().body(this.medicineService.findMedicineByActiveTrue());
    }

    @GetMapping("/public/medicine-category/{categoryId}/medicines/")
    @CrossOrigin
    public ResponseEntity<List<Medicine>> listMedicinesByCategoryId(@PathVariable(value = "categoryId") String categoryId) {
        return new ResponseEntity<>(this.medicineService.findMedicineByCategoryId(Integer.parseInt(categoryId)), HttpStatus.OK);
    }

    @PostMapping(path = "/auth/admin/add-medicine/",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<String> addMedicine(@Valid AddMedicineDTO addMedicineDTO, @RequestPart("avatar") MultipartFile avatar) throws Exception {
        String message = "Có lỗi xảy ra!";
        int check = this.medicineService.addMedicine(addMedicineDTO, avatar);

        if (check == 1) {
            message = "Thêm thuốc thành công!";
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else if (check == 0) {
            message = "Thêm thuốc thất bại!";
        }

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @PostMapping(path = "/auth/admin/update-medicine/",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<String> updateMedicine(@Valid UpdateMedicineDTO updateMedicineDTO, @RequestPart("avatar") MultipartFile avatar) throws Exception {
        String message = "Có lỗi xảy ra!";
        int check = this.medicineService.updateMedicine(updateMedicineDTO, avatar);

        if (check == 1) {
            message = "Cập nhật thuốc thành công!";
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else if (check == 2) {
            message = "Thuốc không tồn tại!";
        } else if (check == 0) {
            message = "Cập nhật thuốc thất bại!";
        }

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/public/search-medicines/")
    @CrossOrigin
    public ResponseEntity<?> listSearchMedicine(@RequestParam Map<String, String> params) {
        return ResponseEntity.ok().body(this.medicineService.findAllMedicinePageSpec(params));
    }

    @GetMapping("/public/medicines/{medicineId}/")
    @CrossOrigin
    public ResponseEntity<Medicine> details(@PathVariable(value = "medicineId") String medicineId) {
        return ResponseEntity.ok().body(this.medicineService.findMedicineByMedicineIdAndActiveTrue(Integer.parseInt(medicineId)));
    }

    @GetMapping("/public/search-medicines-spec/")
    @CrossOrigin
    public ResponseEntity<?> test(@RequestParam Map<String, String> params) {
        return new ResponseEntity<>(this.medicineService.findAllMedicineSpec(params), HttpStatus.OK);
    }

}
