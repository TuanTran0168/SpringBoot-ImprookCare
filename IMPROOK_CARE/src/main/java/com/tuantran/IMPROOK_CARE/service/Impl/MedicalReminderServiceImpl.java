/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.service.Impl;

import com.tuantran.IMPROOK_CARE.models.MedicalReminder;
import com.tuantran.IMPROOK_CARE.models.PrescriptionDetail;
import com.tuantran.IMPROOK_CARE.repository.MedicalReminderRepository;
import com.tuantran.IMPROOK_CARE.service.MedicalReminderService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Administrator
 */
@Service
public class MedicalReminderServiceImpl implements MedicalReminderService {

    @Autowired
    private MedicalReminderRepository medicalReminderRepository;

    @Override
    public List<MedicalReminder> findByPrescriptionDetailId(PrescriptionDetail prescriptionDetailId) {
        return this.medicalReminderRepository.findByPrescriptionDetailId(prescriptionDetailId);
    }

    @Override
    public List<?> findMedicalReminderByPrescriptionId(int prescriptionId) {
        return this.medicalReminderRepository.findMedicalReminderByPrescriptionId(prescriptionId);
    }

    @Override
    public Optional<MedicalReminder> findMedicalReminderByMedicalReminderId(int medicalReminderId) {
        return this.medicalReminderRepository.findByMedicalReminderId(medicalReminderId);
    }

}
