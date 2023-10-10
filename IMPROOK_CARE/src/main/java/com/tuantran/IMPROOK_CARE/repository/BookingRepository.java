/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.repository;

import com.tuantran.IMPROOK_CARE.models.Booking;
import com.tuantran.IMPROOK_CARE.models.ProfilePatient;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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
public interface BookingRepository extends JpaRepository<Booking, Integer> {

    Optional<Booking> findBookingByBookingIdAndActiveTrue(int bookingId);

    Optional<Booking> findBookingByActiveTrueAndBookingCancelFalse();

    //User view
    List<Booking> findBookingByProfilePatientIdAndActiveTrue(ProfilePatient profilePatientId);

//    Optional<Booking> findBookingByProfilePatientIdAndActiveTrue(ProfilePatient profilePatientId);
//    @Query(value = "SELECT u.user_id, u.firstname, pp.name, pp.profile_patient_id, pd.name, pp.name, b.created_date"
//            + "FROM booking b, profile_doctor pd, profile_patient pp, schedule s, user u "
//            + "WHERE b.profile_patient_id = pp.profile_patient_id "
//            + "AND b.schedule_id = s.schedule_id "
//            + "AND s.profile_doctor_id = pd.profile_doctor_id "
//            + "AND u.user_id = pp.user_id "
//            + "AND u.user_id = :userId", nativeQuery = true)
    @Query("SELECT u.userId, u.firstname, pp.name, pp.profilePatientId, pd.name, pp.name, b.createdDate, b.bookingCancel, b.bookingId "
            + "FROM Booking b JOIN b.profilePatientId pp JOIN b.scheduleId s JOIN s.profileDoctorId pd JOIN pp.userId u "
            + "WHERE u.userId = :userId")
    List<Object[]> getBookingForUserView(@Param("userId") int userId);

    @Query("SELECT ts.timeSlotId, ts.timeBegin, ts.timeEnd, s.booked "
            + "FROM Schedule s "
            + "JOIN s.timeSlotId ts "
            + "JOIN s.profileDoctorId pd "
            + "WHERE pd.profileDoctorId = :profileDoctorId "
            + "AND s.date = :date")
    List<Object[]> getTimeSlotsForDoctorOnDate(@Param("profileDoctorId") int profileDoctorId, @Param("date") Date date);

    @Query("SELECT DISTINCT s.date "
            + "FROM Schedule s "
            + "JOIN s.timeSlotId ts "
            + "JOIN s.profileDoctorId pd "
            + "WHERE pd.profileDoctorId = :profileDoctorId")
    List<Date> getDatesForProfileDoctor(@Param("profileDoctorId") int profileDoctorId);

    @Query("SELECT b.bookingId, pd.name, s.date, ts.timeBegin, ts.timeEnd, bs.statusValue, pp.name, u.firstname, u.lastname "
            + "FROM Booking b "
            + "JOIN b.scheduleId s "
            + "JOIN s.profileDoctorId pd "
            + "JOIN b.statusId bs "
            + "JOIN b.profilePatientId pp "
            + "JOIN pp.userId u "
            + "JOIN s.timeSlotId ts "
            + "WHERE pd.profileDoctorId = :profileDoctorId")
    List<Object[]> getBookingForDoctorView(@Param("profileDoctorId") int profileDoctorId);

    @Query("SELECT pd.name, pd.workAddress, pd.specialtyId, pd.bookingPrice, s.date, pp.name, pp.birthday, pp.phonenumber, pp.address, pp.gender, pd.userId, b.bookingCancel, b.statusId, b.bookingId "
            + "FROM Booking b "
            + "JOIN b.scheduleId s "
            + "JOIN b.profilePatientId pp "
            + "JOIN s.profileDoctorId pd "
            + "WHERE b.bookingId = :bookingId")
    List<Object[]> getBookingDetailsByBookingId(@Param("bookingId") int bookingId);

}
