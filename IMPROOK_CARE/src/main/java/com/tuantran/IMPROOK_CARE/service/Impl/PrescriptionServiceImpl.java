/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.service.Impl;

import com.tuantran.IMPROOK_CARE.Specifications.GenericSpecifications;
import com.tuantran.IMPROOK_CARE.dto.AddPrescriptionDTO;
import com.tuantran.IMPROOK_CARE.dto.AddPrescriptionDetailDTO;
import com.tuantran.IMPROOK_CARE.dto.UpdatePrescriptionDTO;
import com.tuantran.IMPROOK_CARE.models.Booking;
import com.tuantran.IMPROOK_CARE.models.BookingStatus;
import com.tuantran.IMPROOK_CARE.models.Medicine;
import com.tuantran.IMPROOK_CARE.models.MedicinePaymentStatus;
import com.tuantran.IMPROOK_CARE.models.PrescriptionDetail;
import com.tuantran.IMPROOK_CARE.models.Prescriptions;
import com.tuantran.IMPROOK_CARE.models.ProfilePatient;
import com.tuantran.IMPROOK_CARE.models.ServicePaymentStatus;
import com.tuantran.IMPROOK_CARE.repository.BookingRepository;
import com.tuantran.IMPROOK_CARE.repository.MedicineRepository;
import com.tuantran.IMPROOK_CARE.repository.PrescriptionDetailRepository;
import com.tuantran.IMPROOK_CARE.repository.PrescriptionRepository;
import com.tuantran.IMPROOK_CARE.service.BookingStatusService;
import com.tuantran.IMPROOK_CARE.service.PrescriptionService;

import jakarta.persistence.NonUniqueResultException;
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
    private BookingStatusService bookingStatusService;

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
    public int addPrescription(AddPrescriptionDTO addPrescriptionDTO,
            Map<String, AddPrescriptionDetailDTO> prescriptionDetailDTO) {
        try {
            Prescriptions prescriptions = new Prescriptions();

            Optional<Booking> bookingOptional = this.bookingRepository
                    .findBookingByBookingIdAndActiveTrue(Integer.parseInt(addPrescriptionDTO.getBookingId()));

            if (bookingOptional.isPresent()) {
                Booking booking = bookingOptional.get();
                BookingStatus bookingStatus = this.bookingStatusService.findBookingStatusByStatusId(4);
                booking.setStatusId(bookingStatus != null ? bookingStatus : new BookingStatus(4));
                prescriptions.setBookingId(booking);
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

                Optional<Medicine> medicineOptional = this.medicineRepository
                        .findMedicineByMedicineIdAndActiveTrue(Integer.parseInt(presDetailDTO.getMedicineId()));

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
        Pageable page = PageRequest.of(defaultPageNumber,
                Integer.parseInt(this.environment.getProperty("spring.data.web.pageable.default-page-size")), mySort);
        if (pageNumber != null && !pageNumber.isEmpty()) {
            page = PageRequest.of(Integer.parseInt(pageNumber),
                    Integer.parseInt(this.environment.getProperty("spring.data.web.pageable.default-page-size")),
                    mySort);
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
        Pageable page = PageRequest.of(defaultPageNumber,
                Integer.parseInt(this.environment.getProperty("spring.data.web.pageable.default-page-size")), mySort);

        List<Specification<Prescriptions>> listSpec = new ArrayList<>();

        if (pageNumber != null && !pageNumber.isEmpty()) {
            if (!pageNumber.equals("NaN")) {
                page = PageRequest.of(Integer.parseInt(pageNumber),
                        Integer.parseInt(this.environment.getProperty("spring.data.web.pageable.default-page-size")),
                        mySort);
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
            // Join<Booking, Schedule> scheduleJoin = bookingJoin.join("scheduleId");
            // Join<Schedule, ProfileDoctor> profileDoctorJoin =
            // scheduleJoin.join("profileDoctorId");
            Predicate profilePatientIdPredicate = criteriaBuilder.equal(profilePatientJoin.get("profilePatientId"),
                    profilePatientId);
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

    @Override
    public Optional<Prescriptions> findByBookingId(Booking bookingId) throws NonUniqueResultException {
        return this.prescriptionRepository.findByBookingId(bookingId);
    }

    @Override
    public int updatePrescription(UpdatePrescriptionDTO updatePrescriptionDTO,
            Map<String, AddPrescriptionDetailDTO> prescriptionDetailDTO) {

        Optional<Prescriptions> prescriptionOptional = this.prescriptionRepository
                .findById(Integer.parseInt(updatePrescriptionDTO.getPrescriptionId()));

        if (prescriptionOptional.isPresent()) {
            Prescriptions prescriptions = prescriptionOptional.get();

            String diagnosis = updatePrescriptionDTO.getDiagnosis();
            String symptom = updatePrescriptionDTO.getSymptom();

            if (diagnosis != null && !diagnosis.isEmpty()) {
                prescriptions.setDiagnosis(diagnosis);
            }

            if (symptom != null && !symptom.isEmpty()) {
                prescriptions.setSymptoms(symptom);
            }

            this.prescriptionRepository.save(prescriptions);

            // Danh sách chi tiết toa thuốc hiện tại
            List<PrescriptionDetail> prescriptionDetails_current = this.prescriptionDetailRepository
                    .findPrescriptionDetailByPrescriptionId(prescriptions);

            // Xóa hết danh sách đó
            this.prescriptionDetailRepository.deleteAllInBatch(prescriptionDetails_current);

            // Duyệt qua danh sách chi tiết toa thuốc mới gửi xuống server để thêm
            // Y chang như thêm mới
            for (AddPrescriptionDetailDTO updatePrescriptionDetailDTO : prescriptionDetailDTO.values()) {

                PrescriptionDetail prescriptionDetail = new PrescriptionDetail();

                Optional<Medicine> medicineOptional = this.medicineRepository
                        .findMedicineByMedicineIdAndActiveTrue(
                                Integer.parseInt(updatePrescriptionDetailDTO.getMedicineId()));

                if (medicineOptional.isPresent()) {
                    prescriptionDetail.setMedicineId(medicineOptional.get());
                } else {
                    return 0;
                }

                prescriptionDetail.setPrescriptionId(prescriptions);
                prescriptionDetail.setMedicineName(updatePrescriptionDetailDTO.getMedicineName());
                prescriptionDetail.setUnitPrice(
                        BigDecimal.valueOf(Double.parseDouble(updatePrescriptionDetailDTO.getUnitPrice())));
                prescriptionDetail.setQuantity(Integer.parseInt(updatePrescriptionDetailDTO.getQuantity()));
                prescriptionDetail.setUsageInstruction(updatePrescriptionDetailDTO.getUsageInstruction());

                prescriptionDetail.setActive(Boolean.TRUE);
                prescriptionDetail.setCreatedDate(new Date());
                prescriptionDetail.setUpdatedDate(new Date());
                this.prescriptionDetailRepository.save(prescriptionDetail);
            }

            return 1;
        } else {
            return 0;
        }
    }
}
