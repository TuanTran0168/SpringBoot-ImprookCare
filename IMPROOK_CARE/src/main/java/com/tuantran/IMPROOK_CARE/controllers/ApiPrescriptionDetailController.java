/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.controllers;

import com.tuantran.IMPROOK_CARE.models.PrescriptionDetail;
import com.tuantran.IMPROOK_CARE.service.PrescriptionDetailService;
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
public class ApiPrescriptionDetailController {

    @Autowired
    private PrescriptionDetailService prescriptionDetailService;
    
    @GetMapping("/auth/prescription/{prescriptionId}/prescription-detail/")
    @CrossOrigin
    public ResponseEntity<List<PrescriptionDetail>> test(@PathVariable(value = "prescriptionId") String prescriptionId) {
        return new ResponseEntity<>(this.prescriptionDetailService.findPrescriptionDetailByPrescriptionId(Integer.parseInt(prescriptionId)), HttpStatus.OK);
    }
}
