/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.service.Impl;

import com.tuantran.IMPROOK_CARE.dto.AddPrescriptionDTO;
import com.tuantran.IMPROOK_CARE.dto.AddPrescriptionDetailDTO;
import com.tuantran.IMPROOK_CARE.models.Booking;
import com.tuantran.IMPROOK_CARE.models.Medicine;
import com.tuantran.IMPROOK_CARE.models.MedicinePaymentStatus;
import com.tuantran.IMPROOK_CARE.models.PrescriptionDetail;
import com.tuantran.IMPROOK_CARE.models.Prescriptions;
import com.tuantran.IMPROOK_CARE.models.ServicePaymentStatus;
import com.tuantran.IMPROOK_CARE.repository.BookingRepository;
import com.tuantran.IMPROOK_CARE.repository.MedicineRepository;
import com.tuantran.IMPROOK_CARE.repository.PrescriptionDetailRepository;
import com.tuantran.IMPROOK_CARE.repository.PrescriptionRepository;
import com.tuantran.IMPROOK_CARE.service.PrescriptionService;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Administrator
 */
@Service
public class PrescriptionServiceImpl implements PrescriptionService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private PrescriptionDetailRepository prescriptionDetailRepository;

    @Autowired
    private MedicineRepository medicineRepository;

    @Override
    public int addPrescription(AddPrescriptionDTO addPrescriptionDTO, Map<String, AddPrescriptionDetailDTO> prescriptionDetailDTO) {
        try {
            Prescriptions prescriptions = new Prescriptions();

            Optional<Booking> bookingOptional = this.bookingRepository.findBookingByBookingIdAndActiveTrue(Integer.parseInt(addPrescriptionDTO.getBookingId()));

            if (bookingOptional.isPresent()) {
                prescriptions.setBookingId(bookingOptional.get());
            } else {
                return 0;
            }
            prescriptions.setDiagnosis(addPrescriptionDTO.getDiagnosis());
            prescriptions.setSymptoms(addPrescriptionDTO.getSymptom());
            prescriptions.setServicePrice(addPrescriptionDTO.getServicePrice());
            prescriptions.setMedicinePaymentStatusId(new MedicinePaymentStatus(1));
            prescriptions.setServicePaymentStatusId(new ServicePaymentStatus(1));
            prescriptions.setActive(Boolean.TRUE);
            prescriptions.setCreatedDate(new Date());
            prescriptions.setPrescriptionDate(new Date());

            this.prescriptionRepository.save(prescriptions);

            for (AddPrescriptionDetailDTO presDetailDTO : prescriptionDetailDTO.values()) {
                PrescriptionDetail prescriptionDetail = new PrescriptionDetail();

                Optional<Medicine> medicineOptional = this.medicineRepository.findMedicineByMedicineIdAndActiveTrue(Integer.parseInt(presDetailDTO.getMedicineId()));

                if (medicineOptional.isPresent()) {
                    prescriptionDetail.setMedicineId(medicineOptional.get());
                } else {
                    return 0;
                }

                prescriptionDetail.setMedicineName(presDetailDTO.getMedicineName());
                prescriptionDetail.setQuantity(Integer.parseInt(presDetailDTO.getQuantity()));
                prescriptionDetail.setUsageInstruction(presDetailDTO.getUsageInstruction());

                prescriptionDetail.setActive(Boolean.TRUE);
                prescriptionDetail.setCreatedDate(new Date());
                this.prescriptionDetailRepository.save(prescriptionDetail);
            }

            return 1;
        } catch (DataAccessException ex) {
            ex.printStackTrace();
            return 0;
        }
    }

}
