/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.repository;

import com.tuantran.IMPROOK_CARE.models.Booking;
import com.tuantran.IMPROOK_CARE.models.ProfilePatient;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Administrator
 */
@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

    Optional<Booking> findBookingByBookingIdAndActiveTrue(int bookingId);

    Optional<Booking> findBookingByActiveTrueAndBookingCancelFalse();
    
    //User view
    Optional<Booking> findBookingByProfilePatientIdAndActiveTrue(ProfilePatient profilePatientId);
    
//    Optional<Booking> findBookingByProfilePatientIdAndActiveTrue(ProfilePatient profilePatientId);
}
