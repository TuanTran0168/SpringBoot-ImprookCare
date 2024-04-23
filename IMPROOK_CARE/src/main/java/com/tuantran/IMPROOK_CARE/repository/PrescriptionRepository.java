/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.repository;

import com.tuantran.IMPROOK_CARE.models.Prescriptions;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Administrator
 */
@Repository
@Transactional
public interface PrescriptionRepository extends JpaRepository<Prescriptions, Integer> {

    Page<Prescriptions> findAll(Specification<Prescriptions> createSpecification, Pageable page);

    Page<Prescriptions> findAll(Specification<Prescriptions> createSpecification);

    // @Query("SELECT p "
    // + "FROM Prescriptions p "
    // + "JOIN p.bookingId b "
    // + "JOIN b.profilePatientId pp "
    // + "JOIN b.scheduleId s "
    // + "JOIN s.profileDoctorId pd "
    // + "WHERE pp.profilePatientId = :profilePatientId")
    // Page<Prescriptions>
    // getPrescriptionsByProfilePatientId(Specification<Prescriptions>
    // createSpecification, Pageable page, @Param("profilePatientId") int
    // profilePatientId);
    // Page<Prescriptions> getPrescriptionsByProfilePatientId(int profilePatientId,
    // Specification<Prescriptions> createSpecification, Pageable page);

    @Query(value = "(select pr.* FROM improokcare.prescriptions pr, improokcare.profile_patient pp, improokcare.booking b "
            +
            "WHERE pr.previous_prescription_id IS NULL " +
            "AND pr.booking_id = b.booking_id " +
            "AND b.profile_patient_id = pp.profile_patient_id " +
            "AND pp.profile_patient_id = :patientId " +
            "AND pr.prescription_id NOT IN (select pr2.previous_prescription_id from improokcare.prescriptions pr2 " +
            "WHERE pr2.previous_prescription_id IS NOT NULL " +
            "AND pr2.previous_prescription_id IN (select pr3.prescription_id FROM improokcare.prescriptions pr3, improokcare.profile_patient pp3, improokcare.booking b3 "
            +
            "WHERE pr3.previous_prescription_id IS NULL " +
            "AND pr3.booking_id = b3.booking_id " +
            "AND b3.profile_patient_id = pp3.profile_patient_id " +
            "AND pp3.profile_patient_id = :patientId))) " +
            "UNION " +
            "(SELECT pr.* FROM improokcare.prescriptions pr, improokcare.profile_patient pp, improokcare.booking b " +
            "WHERE pr.booking_id = b.booking_id " +
            "AND b.profile_patient_id = pp.profile_patient_id " +
            "AND pp.profile_patient_id = :patientId " +
            "AND pr.previous_prescription_id IN (select pr4.prescription_id FROM improokcare.prescriptions pr4, improokcare.profile_patient pp4, improokcare.booking b4 "
            +
            "WHERE pr4.booking_id = b4.booking_id " +
            "AND b4.profile_patient_id = pp4.profile_patient_id " +
            "AND pp4.profile_patient_id = :patientId " +
            "AND pr4.previous_prescription_id IS NULL)) " +
            "ORDER BY pr.prescription_id DESC " +
            "LIMIT 1)", nativeQuery = true)
    Page<?> findCustomPrescriptions(@Param("patientId") int patientId, Pageable page);

    @Query(value = "(SELECT pr.* FROM improokcare.prescriptions pr " +
            "JOIN improokcare.booking b ON pr.booking_id = b.booking_id " +
            "JOIN improokcare.profile_patient pp ON b.profile_patient_id.profile_patient_id = pp.profile_patient_id " +
            "WHERE pr.previous_prescription_id IS NULL " +
            "AND pp.profile_patient_id = :patientId " +
            "AND pr.prescription_id NOT IN ( " +
            "    SELECT pr2.previous_prescription_id FROM improokcare.prescriptions pr2 " +
            "    WHERE pr2.previous_prescription_id IS NOT NULL " +
            "    AND pr2.previous_prescription_id IN ( " +
            "        SELECT pr3.prescription_id FROM improokcare.prescriptions pr3 " +
            "        JOIN improokcare.booking b3 ON pr3.booking_id = b3.booking_id " +
            "        JOIN improokcare.profile_patient pp3 ON b3.profile_patient_id.profile_patient_id = pp3.profile_patient_id "
            +
            "        WHERE pr3.previous_prescription_id IS NULL " +
            "        AND pp3.profile_patient_id = :patientId " +
            "    ) " +
            ")) " +
            "UNION " +
            "(SELECT pr.* FROM improokcare.prescriptions pr " +
            "JOIN improokcare.booking b ON pr.booking_id = b.booking_id " +
            "JOIN improokcare.profile_patient pp ON b.profile_patient_id.profile_patient_id = pp.profile_patient_id " +
            "WHERE pr.previous_prescription_id IN ( " +
            "    SELECT pr4.prescription_id FROM improokcare.prescriptions pr4 " +
            "    JOIN improokcare.booking b4 ON pr4.booking_id = b4.booking_id " +
            "    JOIN improokcare.profile_patient pp4 ON b4.profile_patient_id = pp4.profile_patient_id " +
            "    WHERE pr4.previous_prescription_id IS NULL " +
            "    AND pp4.profile_patient_id = :patientId " +
            ")) " +
            "ORDER BY pr.prescription_id DESC " +
            "LIMIT 1)", nativeQuery = true)
    List<?> findCustomPrescriptionsList(@Param("patientId") int patientId);

}
