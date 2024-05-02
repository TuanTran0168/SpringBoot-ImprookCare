/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.controllers;

import com.tuantran.IMPROOK_CARE.dto.MedicalReminderDTO;
import com.tuantran.IMPROOK_CARE.dto.PrescriptionDetailReminderDTO;
import com.tuantran.IMPROOK_CARE.models.MedicalReminder;
import com.tuantran.IMPROOK_CARE.models.PrescriptionDetail;
import com.tuantran.IMPROOK_CARE.models.TimeReminder;
import com.tuantran.IMPROOK_CARE.service.MedicalReminderService;
import com.tuantran.IMPROOK_CARE.service.PrescriptionDetailService;

import java.util.ArrayList;
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

    @Autowired
    private MedicalReminderService medicalReminderService;

    @GetMapping("/auth/prescription/{prescriptionId}/prescription-detail/")
    @CrossOrigin
    public ResponseEntity<List<PrescriptionDetail>> findPrescriptionDetailByPrescriptionId(
            @PathVariable(value = "prescriptionId") String prescriptionId) {
        return new ResponseEntity<>(
                this.prescriptionDetailService.findPrescriptionDetailByPrescriptionId(
                        Integer.parseInt(prescriptionId)),
                HttpStatus.OK);
    }

    @GetMapping("/auth/prescription/{prescriptionId}/prescription-detail-reminder/")
    @CrossOrigin
    public ResponseEntity<?> findPrescriptionDetailByPrescriptionIdReminder(
            @PathVariable(value = "prescriptionId") String prescriptionId) {

        List<PrescriptionDetailReminderDTO> listPrescriptionDetailReminderDTOs = new ArrayList<>();

        List<PrescriptionDetail> listPrescriptionDetails = this.prescriptionDetailService
                .findPrescriptionDetailByPrescriptionId(Integer.parseInt(prescriptionId));

        for (PrescriptionDetail prescriptionDetail : listPrescriptionDetails) {
            PrescriptionDetailReminderDTO prescriptionDetailReminderDTO = new PrescriptionDetailReminderDTO();
            prescriptionDetailReminderDTO.setPrescriptionDetail(prescriptionDetail);

            // Có thể dùng luôn list này nhưng nó quá dài
            List<MedicalReminder> listMedicalReminders = this.medicalReminderService
                    .findByPrescriptionDetailId(prescriptionDetail);

            List<MedicalReminderDTO> listMedicalReminderDTOs = new ArrayList<>();

            for (MedicalReminder medicalReminder : listMedicalReminders) {
                MedicalReminderDTO medicalReminderDTO = new MedicalReminderDTO();

                medicalReminderDTO.setMedicalReminderId(
                        String.valueOf(medicalReminder.getMedicalReminderId()));
                medicalReminderDTO.setPrescriptionDetailId(
                        String.valueOf(medicalReminder.getPrescriptionDetailId()
                                .getPrescriptionDetailId()));

                medicalReminderDTO.setTimeReminderId(medicalReminder.getTimeReminderId());

                listMedicalReminderDTOs.add(medicalReminderDTO);
            }

            prescriptionDetailReminderDTO.setTimeReminders(listMedicalReminderDTOs);

            listPrescriptionDetailReminderDTOs.add(prescriptionDetailReminderDTO);
        }

        return new ResponseEntity<>(listPrescriptionDetailReminderDTOs,
                HttpStatus.OK);
    }
}
