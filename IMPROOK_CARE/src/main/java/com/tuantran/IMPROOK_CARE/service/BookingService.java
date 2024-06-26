/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.service;

import com.tuantran.IMPROOK_CARE.dto.BookingDTO;
import com.tuantran.IMPROOK_CARE.models.Booking;
import com.tuantran.IMPROOK_CARE.models.ProfilePatient;
import com.tuantran.IMPROOK_CARE.models.Schedule;
import com.tuantran.IMPROOK_CARE.models.TimeSlot;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Administrator
 */
public interface BookingService {

        int addBooking(BookingDTO bookingDTO);

        int cancelBooking(int bookingId);

        int acceptBooking(int bookingId);

        int denyBooking(int bookingId);

        // List<Booking> findBookingForUserView(int userId);
        List<Object[]> getBookingForUserView(@Param("userId") int userId);

        Page<?> getBookingForUserView(@Param("userId") int userId, @Param("bookingStatus") int bookingStatusId,
                        Pageable page);

        Page<?> getBookingByUserId(@Param("userId") int userId, Pageable page);

        Page<?> getBookingForUserViewDoubleStatus(@Param("userId") int userId,
                        @Param("bookingStatusId1") int bookingStatusId1,
                        @Param("bookingStatusId2") int bookingStatusId2,
                        Pageable page);

        List<Object[]> getTimeSlotsForDoctorOnDate(@Param("profileDoctorId") int profileDoctorId,
                        @Param("date") String date);

        List<?> getDatesForProfileDoctor(@Param("profileDoctorId") int profileDoctorId);

        List<Object[]> getBookingForDoctorView(@Param("profileDoctorId") int profileDoctorId);

        Page<?> getBookingForDoctorViewPage(@Param("profileDoctorId") int profileDoctorId,
                        @Param("bookingStatusId") int bookingStatusId, Map<String, String> params);

        List<Object[]> getBookingDetailsByBookingId(@Param("bookingId") int bookingId);

        int softDeleteBooking(int bookingId);

        Booking cancelBooking(Booking booking);

        Booking acceptBooking(Booking booking);

        Optional<Booking> findBookingByBookingIdAndActiveTrue(int bookingId);

        Booking createBooking(Booking booking, Schedule schedule, ProfilePatient profilePatient);

        Booking createBookingReExamination(TimeSlot timeSlot, Booking booking, Schedule schedule,
                        ProfilePatient profilePatient);
}
