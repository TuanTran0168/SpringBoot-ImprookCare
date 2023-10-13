/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.service.Impl;

import com.tuantran.IMPROOK_CARE.components.datetime.DateFormatComponent;
import com.tuantran.IMPROOK_CARE.dto.BookingDTO;
import com.tuantran.IMPROOK_CARE.models.Booking;
import com.tuantran.IMPROOK_CARE.models.BookingStatus;
import com.tuantran.IMPROOK_CARE.models.ProfilePatient;
import com.tuantran.IMPROOK_CARE.models.Schedule;
import com.tuantran.IMPROOK_CARE.models.User;
import com.tuantran.IMPROOK_CARE.repository.BookingRepository;
import com.tuantran.IMPROOK_CARE.repository.BookingStatusRepository;
import com.tuantran.IMPROOK_CARE.repository.ProfilePatientRepository;
import com.tuantran.IMPROOK_CARE.repository.ScheduleRepository;
import com.tuantran.IMPROOK_CARE.service.BookingService;
import jakarta.persistence.criteria.Join;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private DateFormatComponent dateFormatComponent;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int addBooking(BookingDTO bookingDTO) {
        try {
            Booking booking = new Booking();
            Optional<Schedule> scheduleOptional = this.scheduleRepository.findScheduleByScheduleIdAndActiveTrue(Integer.parseInt(bookingDTO.getScheduleId()));

            if (scheduleOptional.isPresent()) {
                Schedule schedule = scheduleOptional.get();
                booking.setScheduleId(schedule);

                schedule.setBooked(Boolean.TRUE);
                this.scheduleRepository.save(schedule);
            } else {
                return 0;
            }

            Optional<ProfilePatient> profilePatientOptional = this.profilePatientRepository.findProfilePatientByProfilePatientIdAndActiveTrue(Integer.parseInt(bookingDTO.getProfilePatientId()));

            if (profilePatientOptional.isPresent()) {
                booking.setProfilePatientId(profilePatientOptional.get());
            } else {
                return 0;
            }

            booking.setStatusId(this.bookingStatusRepository.findBookingStatusByStatusId(1).get());
            booking.setCreatedDate(new Date());

            booking.setBookingCancel(Boolean.FALSE);
            booking.setActive(Boolean.TRUE);
            this.bookingRepository.save(booking);
            return 1;
        } catch (DataAccessException ex) {
            ex.printStackTrace();
            return 0;
        } catch (NoSuchElementException ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int cancelBooking(int bookingId) {
        try {
            Optional<Booking> bookingOptional = this.bookingRepository.findBookingByBookingIdAndActiveTrue(bookingId);

            if (bookingOptional.isPresent()) {
                Booking booking = bookingOptional.get();

                Optional<Schedule> scheduleOptional = this.scheduleRepository.findScheduleByScheduleIdAndActiveTrue(booking.getScheduleId().getScheduleId());

                if (scheduleOptional.isPresent()) {
                    Schedule schedule = scheduleOptional.get();
                    schedule.setBooked(Boolean.FALSE);
                    this.scheduleRepository.save(schedule);
                } else {
                    return 0;
                }

                booking.setBookingCancel(Boolean.TRUE);
                booking.setUpdatedDate(new Date());
                this.bookingRepository.save(booking);
                return 1;
            }
            return 0;
        } catch (NoSuchElementException ex) {
            Logger.getLogger(BookingServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }

    }

    @Override
    public int acceptBooking(int bookingId) {
        try {
            Optional<Booking> bookingOptional = this.bookingRepository.findBookingByBookingIdAndActiveTrue(bookingId);

            if (bookingOptional.isPresent()) {
                Booking booking = bookingOptional.get();
                booking.setStatusId(new BookingStatus(2));
                booking.setUpdatedDate(new Date());
                this.bookingRepository.save(booking);
                return 1;
            } else {
                return 0;
            }

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
                booking.setUpdatedDate(new Date());
                this.bookingRepository.save(booking);
                return 1;
            }
            return 0;
        } catch (NoSuchElementException ex) {
            Logger.getLogger(BookingServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

//    @Override
//    public List<Booking> findBookingForUserView(int userId) {
//        Specification<Booking> specification = (root, query, criteriaBuilder) -> {
//            Join<Booking, ProfilePatient> profilePatientJoin = root.join("profilePatient");
//            
//            Join<ProfilePatient, User> userJoin = profilePatientJoin.join("user");
//            
//            return criteriaBuilder.equal(userJoin.get("userId"), userId);
//        };
//        
//        return this.joinBookingRepository.findBookingForUserView(specification);
//    }
    @Override
    public List<Object[]> getBookingForUserView(int userId) {
        return this.bookingRepository.getBookingForUserView(userId);
    }

    @Override
    public List<Object[]> getTimeSlotsForDoctorOnDate(int profileDoctorId, String date) {
        try {
            Date date_parse = this.dateFormatComponent.myDateFormat().parse(date);
            return this.bookingRepository.getTimeSlotsForDoctorOnDate(profileDoctorId, date_parse);
        } catch (ParseException ex) {
            Logger.getLogger(BookingServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return new ArrayList<>();
        }
    }

    @Override
    public List<Date> getDatesForProfileDoctor(int profileDoctorId) {
        return this.bookingRepository.getDatesForProfileDoctor(profileDoctorId);
    }

    @Override
    public List<Object[]> getBookingForDoctorView(int profileDoctorId) {
        return this.bookingRepository.getBookingForDoctorView(profileDoctorId);
    }

    @Override
    public List<Object[]> getBookingDetailsByBookingId(int bookingId) {
        return this.bookingRepository.getBookingDetailsByBookingId(bookingId);
    }

    @Override
    @Transactional
    public int softDeleteBooking(int bookingId) {
        Optional<Booking> bookingOptional = this.bookingRepository.findBookingByBookingIdAndActiveTrue(bookingId);
        if (bookingOptional.isPresent()) {
            Booking booking = bookingOptional.get();
            if (booking.getActive().equals(Boolean.TRUE)) {
                booking.setActive(Boolean.FALSE);
                return 1;
            } else {
                System.out.println("Booking: " + booking.getActive());
                return 2;
            }
        } else {
            return 3; // Không tìm được để xóa
        }
    }

}
