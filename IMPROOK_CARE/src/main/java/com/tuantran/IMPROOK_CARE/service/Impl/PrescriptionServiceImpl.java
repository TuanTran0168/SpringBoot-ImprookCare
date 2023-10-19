/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.service.Impl;

import com.tuantran.IMPROOK_CARE.Specifications.GenericSpecifications;
import com.tuantran.IMPROOK_CARE.dto.AddPrescriptionDTO;
import com.tuantran.IMPROOK_CARE.dto.AddPrescriptionDetailDTO;
import com.tuantran.IMPROOK_CARE.models.Booking;
import com.tuantran.IMPROOK_CARE.models.Medicine;
import com.tuantran.IMPROOK_CARE.models.MedicinePaymentStatus;
import com.tuantran.IMPROOK_CARE.models.PrescriptionDetail;
import com.tuantran.IMPROOK_CARE.models.Prescriptions;
import com.tuantran.IMPROOK_CARE.models.ProfileDoctor;
import com.tuantran.IMPROOK_CARE.models.ProfilePatient;
import com.tuantran.IMPROOK_CARE.models.Schedule;
import com.tuantran.IMPROOK_CARE.models.ServicePaymentStatus;
import com.tuantran.IMPROOK_CARE.repository.BookingRepository;
import com.tuantran.IMPROOK_CARE.repository.MedicineRepository;
import com.tuantran.IMPROOK_CARE.repository.PrescriptionDetailRepository;
import com.tuantran.IMPROOK_CARE.repository.PrescriptionRepository;
import com.tuantran.IMPROOK_CARE.service.PrescriptionService;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private Environment environment;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
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

                prescriptionDetail.setPrescriptionId(prescriptions);
                prescriptionDetail.setMedicineName(presDetailDTO.getMedicineName());
                prescriptionDetail.setUnitPrice(BigDecimal.valueOf(Double.parseDouble(presDetailDTO.getUnitPrice())));
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

    @Override
    public Page<Prescriptions> findAllPrescriptionPageSpec(Map<String, String> params) {
        String pageNumber = params.get("pageNumber");
        String diagnosis = params.get("diagnosis");
        String symptoms = params.get("symptoms");
        String fromPrice = params.get("fromPrice");
        String toPrice = params.get("toPrice");

        List<Specification<Prescriptions>> listSpec = new ArrayList<>();
        int defaultPageNumber = 0;
        Sort mySort = Sort.by("createdDate").descending();
        Pageable page = PageRequest.of(defaultPageNumber, Integer.parseInt(this.environment.getProperty("spring.data.web.pageable.default-page-size")), mySort);
        if (pageNumber != null && !pageNumber.isEmpty()) {
            page = PageRequest.of(Integer.parseInt(pageNumber), Integer.parseInt(this.environment.getProperty("spring.data.web.pageable.default-page-size")), mySort);
        }

        if (diagnosis != null && !diagnosis.isEmpty()) {
            Specification<Prescriptions> spec = GenericSpecifications.fieldContains("diagnosis", diagnosis);
            listSpec.add(spec);
        }

        if (symptoms != null && !symptoms.isEmpty()) {
            Specification<Prescriptions> spec = GenericSpecifications.fieldContains("symptoms", symptoms);
            listSpec.add(spec);
        }

        if (fromPrice != null && !fromPrice.isEmpty()) {
            Specification<Prescriptions> spec = GenericSpecifications.greaterThan("servicePrice", fromPrice);
            listSpec.add(spec);
        }

        if (toPrice != null && !toPrice.isEmpty()) {
            Specification<Prescriptions> spec = GenericSpecifications.lessThan("servicePrice", toPrice);
            listSpec.add(spec);
        }

        Specification<Prescriptions> spec = GenericSpecifications.fieldEquals("active", Boolean.TRUE);
        listSpec.add(spec);

        return this.prescriptionRepository.findAll(GenericSpecifications.createSpecification(listSpec), page);
    }

    @Override
    public Page<Prescriptions> getPrescriptionsByProfilePatientIdPageSpec(Map<String, String> params) {
        String pageNumber = params.get("pageNumber");
        String profilePatientId = params.get("profilePatientId");
        String diagnosis = params.get("diagnosis");
        String symptoms = params.get("symptoms");

        int defaultPageNumber = 0;
        Sort mySort = Sort.by("createdDate").descending();
        Pageable page = PageRequest.of(defaultPageNumber, Integer.parseInt(this.environment.getProperty("spring.data.web.pageable.default-page-size")), mySort);

        List<Specification<Prescriptions>> listSpec = new ArrayList<>();

        if (pageNumber != null && !pageNumber.isEmpty()) {
            if (!pageNumber.equals("NaN")) {
                page = PageRequest.of(Integer.parseInt(pageNumber), Integer.parseInt(this.environment.getProperty("spring.data.web.pageable.default-page-size")), mySort);
            }
        }

        if (diagnosis != null && !diagnosis.isEmpty()) {
            Specification<Prescriptions> spec = GenericSpecifications.fieldContains("diagnosis", diagnosis);
            listSpec.add(spec);
        }

        if (symptoms != null && !symptoms.isEmpty()) {
            Specification<Prescriptions> spec = GenericSpecifications.fieldContains("symptoms", symptoms);
            listSpec.add(spec);
        }

        Specification<Prescriptions> specificationProMax = (root, query, criteriaBuilder) -> {
            Join<Prescriptions, Booking> bookingJoin = root.join("bookingId");
            Join<Booking, ProfilePatient> profilePatientJoin = bookingJoin.join("profilePatientId");
            Join<Booking, Schedule> scheduleJoin = bookingJoin.join("scheduleId");
            Join<Schedule, ProfileDoctor> profileDoctorJoin = scheduleJoin.join("profileDoctorId");
            Predicate profilePatientIdPredicate = criteriaBuilder.equal(profilePatientJoin.get("profilePatientId"), profilePatientId);
            return criteriaBuilder.and(profilePatientIdPredicate);
        };

        listSpec.add(specificationProMax);
        return this.prescriptionRepository.findAll(GenericSpecifications.createSpecification(listSpec), page);
    }

    @Override
    public int payMedicine(int prescriptionId, String medicine_payment_TxnRef) {
        try {
            Optional<Prescriptions> prescriptionOptional = this.prescriptionRepository.findById(prescriptionId);
            if (prescriptionOptional.isPresent()) {
                Prescriptions prescription = prescriptionOptional.get();
                prescription.setMedicinePaymentStatusId(new MedicinePaymentStatus(2));
                prescription.setMedicinepaymentTxnRef(medicine_payment_TxnRef);
                this.prescriptionRepository.save(prescription);
                return 1;
            }
            return 0;
        } catch (NoSuchElementException ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    @Override
    public int payService(int prescriptionId, String service_payment_TxnRef) {
        try {
            Optional<Prescriptions> prescriptionOptional = this.prescriptionRepository.findById(prescriptionId);
            if (prescriptionOptional.isPresent()) {
                Prescriptions prescription = prescriptionOptional.get();
                prescription.setServicePaymentStatusId(new ServicePaymentStatus(2));
                prescription.setServicepaymentTxnRef(service_payment_TxnRef);
                this.prescriptionRepository.save(prescription);
                return 1;
            }
            return 0;
        } catch (NoSuchElementException ex) {
            ex.printStackTrace();
            return 0;
        }
    }
}
