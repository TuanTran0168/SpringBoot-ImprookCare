/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.service;

import com.tuantran.IMPROOK_CARE.dto.AddPrescriptionDTO;
import com.tuantran.IMPROOK_CARE.dto.AddPrescriptionDetailDTO;
import com.tuantran.IMPROOK_CARE.dto.UpdatePrescriptionDTO;
import com.tuantran.IMPROOK_CARE.models.Booking;
import com.tuantran.IMPROOK_CARE.models.Prescriptions;

import jakarta.persistence.NonUniqueResultException;

import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;

/**
 *
 * @author Administrator
 */
public interface PrescriptionService {

        int addPrescription(AddPrescriptionDTO addPrescriptionDTO,
                        Map<String, AddPrescriptionDetailDTO> prescriptionDetailDTO);

        Page<Prescriptions> findAllPrescriptionPageSpec(Map<String, String> params);

        Page<Prescriptions> getPrescriptionsByProfilePatientIdPageSpec(Map<String, String> params);

        int payMedicine(int prescriptionId, String medicine_payment_TxnRef);

        int payService(int prescriptionId, String service_payment_TxnRef);

        Optional<Prescriptions> findByBookingId(Booking bookingId) throws NonUniqueResultException;

        int updatePrescription(UpdatePrescriptionDTO updatePrescriptionDTO,
                        Map<String, AddPrescriptionDetailDTO> prescriptionDetailDTO);
}
