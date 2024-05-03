/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.service.Impl;

import com.tuantran.IMPROOK_CARE.models.MedicalReminder;
import com.tuantran.IMPROOK_CARE.models.MedicalSchedule;
import com.tuantran.IMPROOK_CARE.models.PrescriptionDetail;
import com.tuantran.IMPROOK_CARE.repository.MedicalReminderRepository;
import com.tuantran.IMPROOK_CARE.repository.MedicalScheduleRepository;
import com.tuantran.IMPROOK_CARE.service.MedicalReminderService;
import com.tuantran.IMPROOK_CARE.service.MedicalScheduleService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 *
 * @author Administrator
 */
@Service
public class MedicalScheduleServiceImpl implements MedicalScheduleService {

    @Autowired
    private MedicalReminderRepository medicalReminderRepository;

    @Autowired
    private MedicalScheduleRepository medicalScheduleRepository;

    @Autowired
    private Environment environment;

    @Override
    public Optional<MedicalSchedule> findByMedicalScheduleIdAndActiveTrue(Integer medicalScheduleId) {
        return this.medicalScheduleRepository.findByMedicalScheduleIdAndActiveTrue(medicalScheduleId);
    }

    @Override
    public List<MedicalSchedule> findByMedicalReminderIdAndActiveTrue(MedicalReminder medicalReminderId) {
        return this.medicalScheduleRepository.findByMedicalReminderIdAndActiveTrue(medicalReminderId);
    }

    @Override
    public Page<?> findAllMedicalSchedulePageSpec(Specification<?> createSpecification, Pageable page) {
        return this.medicalScheduleRepository.findAll(createSpecification, page);
    }

    @Override
    public List<?> findMedicalScheduleByPrescriptionId(int prescriptionId) {
        return this.medicalScheduleRepository.findMedicalScheduleByPrescriptionId(prescriptionId);
    }

    @Override
    public MedicalSchedule addMedicalSchedule(MedicalSchedule medicalSchedule) {
        return this.medicalScheduleRepository.save(medicalSchedule);
    }

    @Override
    public MedicalSchedule updateMedicalSchedule(MedicalSchedule medicalSchedule) {
        return this.medicalScheduleRepository.save(medicalSchedule);
    }

}
