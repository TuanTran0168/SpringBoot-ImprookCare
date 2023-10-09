/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.controllers;

import com.tuantran.IMPROOK_CARE.dto.AddPrescriptionAndDetailsDTO;
import com.tuantran.IMPROOK_CARE.dto.AddPrescriptionDTO;
import com.tuantran.IMPROOK_CARE.dto.AddPrescriptionDetailDTO;
import com.tuantran.IMPROOK_CARE.service.PrescriptionService;
import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Administrator
 */
@RestController
@RequestMapping("/api")
public class ApiPrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;

//    @PostMapping("/auth/doctor/add-prescription/")
//    @CrossOrigin
//    public ResponseEntity<String> addPrescription(@Valid @RequestBody AddPrescriptionDTO addPrescriptionDTO, @Valid @RequestBody Map<String, AddPrescriptionDetailDTO> prescriptionDetailDTO) {
//        String message = "Có lỗi xảy ra!";
//        int check = this.prescriptionService.addPrescription(addPrescriptionDTO, prescriptionDetailDTO);
//
//        if (check == 1) {
//            message = "Thêm đơn thuốc thành công!";
//            return new ResponseEntity<>(message, HttpStatus.OK);
//        } else if (check == 0) {
//            message = "Đơn thuốc thất bại!";
//        }
//
//        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
//    }
    @PostMapping("/auth/doctor/add-prescription/")
//    @PostMapping(path = "/auth/doctor/add-prescription/",
//            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
//            produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<String> addPrescription(@RequestBody AddPrescriptionAndDetailsDTO addPrescriptionAndDetailsDTO) {
        String message = "Có lỗi xảy ra!";
        AddPrescriptionDTO addPrescriptionDTO = addPrescriptionAndDetailsDTO.getAddPrescriptionDTO();
        Map<String, AddPrescriptionDetailDTO> prescriptionDetailDTO = addPrescriptionAndDetailsDTO.getPrescriptionDetailDTO();
        int check = this.prescriptionService.addPrescription(addPrescriptionDTO, prescriptionDetailDTO);

        if (check == 1) {
            message = "Thêm đơn thuốc thành công!";
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else if (check == 0) {
            message = "Thêm đơn thuốc thất bại!";
        }

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}
