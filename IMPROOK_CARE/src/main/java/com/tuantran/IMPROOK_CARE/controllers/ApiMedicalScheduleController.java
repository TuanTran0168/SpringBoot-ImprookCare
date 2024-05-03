/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.controllers;

import com.tuantran.IMPROOK_CARE.models.Districts;
import com.tuantran.IMPROOK_CARE.models.Provinces;
import com.tuantran.IMPROOK_CARE.models.Wards;
import com.tuantran.IMPROOK_CARE.service.DistrictsService;
import com.tuantran.IMPROOK_CARE.service.MedicalReminderService;
import com.tuantran.IMPROOK_CARE.service.MedicalScheduleService;
import com.tuantran.IMPROOK_CARE.service.ProvincesService;
import com.tuantran.IMPROOK_CARE.service.WardsService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ApiMedicalScheduleController {

    @Autowired
    private MedicalScheduleService medicalScheduleService;

    @GetMapping("/auth/prescriptionId/{prescriptionId}/medical-schedule/")
    @CrossOrigin
    public ResponseEntity<?> listMedicalReminder(
            @PathVariable(value = "prescriptionId") int prescriptionId) {
        return ResponseEntity.ok()
                .body(this.medicalScheduleService.findMedicalScheduleByPrescriptionId(prescriptionId));
    }
}
