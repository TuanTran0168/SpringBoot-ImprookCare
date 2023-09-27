/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.service.Impl;

import com.tuantran.IMPROOK_CARE.dto.BookingDTO;
import com.tuantran.IMPROOK_CARE.models.Booking;
import com.tuantran.IMPROOK_CARE.models.BookingStatus;
import com.tuantran.IMPROOK_CARE.repository.BookingRepository;
import com.tuantran.IMPROOK_CARE.repository.BookingStatusRepository;
import com.tuantran.IMPROOK_CARE.repository.ProfilePatientRepository;
import com.tuantran.IMPROOK_CARE.repository.ScheduleRepository;
import com.tuantran.IMPROOK_CARE.service.BookingService;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Administrator
 */
@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private ProfilePatientRepository profilePatientRepository;

    @Autowired
    private BookingStatusRepository bookingStatusRepository;

    @Override
    public int addBooking(BookingDTO bookingDTO) {
        try {
            Booking booking = new Booking();
            booking.setScheduleId(this.scheduleRepository.findScheduleByScheduleIdAndActiveTrue(Integer.parseInt(bookingDTO.getScheduleId())).get());
            booking.setProfilePatientId(this.profilePatientRepository.findProfilePatientByProfilePatientIdAndActiveTrue(Integer.parseInt(bookingDTO.getProfilePatientId())).get());
            booking.setCreatedDate(new Date());
            booking.setStatusId(this.bookingStatusRepository.findBookingStatusByStatusId(1).get());
            booking.setBookingCancel(Boolean.FALSE);
            this.bookingRepository.save(booking);
            return 1;
        } catch (DataAccessException ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    @Override
    public int acceiptBooking(int bookingId) {
        try {
            Optional<Booking> bookingOptional = this.bookingRepository.findBookingByBookingIdAndActiveTrue(bookingId);

            if (bookingOptional.isPresent()) {
                Booking booking = bookingOptional.get();
                booking.setStatusId(new BookingStatus(2));
                return 1;
            }
            return 0;
        } catch (NoSuchElementException ex) {
            Logger.getLogger(BookingServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }

    }

    @Override
    public int denyBooking(int bookingId) {
        try {
            Optional<Booking> bookingOptional = this.bookingRepository.findBookingByBookingIdAndActiveTrue(bookingId);

            if (bookingOptional.isPresent()) {
                Booking booking = bookingOptional.get();
                booking.setStatusId(new BookingStatus(3));
                return 1;
            }
            return 0;
        } catch (NoSuchElementException ex) {
            Logger.getLogger(BookingServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

}
