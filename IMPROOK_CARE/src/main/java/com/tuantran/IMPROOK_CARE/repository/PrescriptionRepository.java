/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.repository;

import com.tuantran.IMPROOK_CARE.models.Booking;
import com.tuantran.IMPROOK_CARE.models.Prescriptions;

import jakarta.persistence.NonUniqueResultException;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
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

    Optional<Prescriptions> findByBookingId(Booking bookingId) throws NonUniqueResultException;

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
}
