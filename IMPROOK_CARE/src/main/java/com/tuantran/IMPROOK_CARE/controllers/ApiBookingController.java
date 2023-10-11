/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.controllers;

import com.tuantran.IMPROOK_CARE.dto.BookingDTO;
import com.tuantran.IMPROOK_CARE.service.BookingService;
import jakarta.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Administrator
 */
@RestController
@RequestMapping("/api")
public class ApiBookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/auth/add-booking/")
    @CrossOrigin
    public ResponseEntity<String> addBooking(@Valid @RequestBody BookingDTO bookingDTO) {
        String message = "Có lỗi xảy ra!";
        int check = this.bookingService.addBooking(bookingDTO);

        if (check == 1) {
            message = "Đặt lịch thành công!";
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else if (check == 0) {
            message = "Đặt lịch thất bại!";
        }

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/auth/booking-user-view/")
    @CrossOrigin
    public ResponseEntity<List<Object[]>> getBookingForUserView(@RequestBody Map<String, String> params) {
        String userId = params.get("userId");
        return new ResponseEntity<>(this.bookingService.getBookingForUserView(Integer.parseInt(userId)), HttpStatus.OK);
    }

    @PostMapping("/public/time-slot-booking/")
    @CrossOrigin
    public ResponseEntity<List<Object[]>> getTimeSlotsForDoctorOnDate(@RequestBody Map<String, String> params) {
        String profileDoctorId = params.get("profileDoctorId");
        String date = params.get("date");
        return new ResponseEntity<>(this.bookingService.getTimeSlotsForDoctorOnDate(Integer.parseInt(profileDoctorId), date), HttpStatus.OK);
    }

    @PostMapping("/public/date-booking/")
    @CrossOrigin
    public ResponseEntity<List<Date>> getDatesForProfileDoctor(@RequestBody Map<String, String> params) {
        String profileDoctorId = params.get("profileDoctorId");
        return new ResponseEntity<>(this.bookingService.getDatesForProfileDoctor(Integer.parseInt(profileDoctorId)), HttpStatus.OK);
    }

    @PostMapping("/auth/doctor/accept-booking/")
    @CrossOrigin
    public ResponseEntity<String> acceptBooking(@RequestBody String bookingId) {

        String message = "Có lỗi xảy ra!";
        int check = this.bookingService.acceptBooking(Integer.parseInt(bookingId));

        if (check == 1) {
            message = "Xác nhận thành công lịch đặt khám!";
            return new ResponseEntity<>(message, HttpStatus.OK);
        }

        if (check == 0) {
            message = "Xác nhận thất bại lịch đặt khám!";
        }

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/auth/doctor/deny-booking/")
    @CrossOrigin
    public ResponseEntity<String> denyBooking(@RequestBody String bookingId) {

        String message = "Có lỗi xảy ra!";
        int check = this.bookingService.denyBooking(Integer.parseInt(bookingId));

        if (check == 1) {
            message = "Từ chối thành công lịch đặt khám!";
            return new ResponseEntity<>(message, HttpStatus.OK);
        }

        if (check == 0) {
            message = "Từ chối thất bại lịch đặt khám!";
        }

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/auth/cancel-booking/")
    @CrossOrigin
    public ResponseEntity<String> cancelBooking(@RequestBody String bookingId) {

        String message = "Có lỗi xảy ra!";
        int check = this.bookingService.cancelBooking(Integer.parseInt(bookingId));

        if (check == 1) {
            message = "Hủy thành công lịch đặt khám!";
            return new ResponseEntity<>(message, HttpStatus.OK);
        }

        if (check == 0) {
            message = "Hủy thất bại lịch đặt khám!";
        }

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/auth/booking-doctor-view/")
    @CrossOrigin
    public ResponseEntity<List<Object[]>> getBookingForDoctorView(@RequestBody Map<String, String> params) {
        String profiledoctorId = params.get("profileDoctorId");
        return new ResponseEntity<>(this.bookingService.getBookingForDoctorView(Integer.parseInt(profiledoctorId)), HttpStatus.OK);
    }

    @PostMapping("/auth/booking-details-user-view/")
    @CrossOrigin
    public ResponseEntity<List<Object[]>> getBookingDetailsByBookingId(@RequestBody Map<String, String> params) {
        String bookingId = params.get("bookingId");
        return new ResponseEntity<>(this.bookingService.getBookingDetailsByBookingId(Integer.parseInt(bookingId)), HttpStatus.OK);
    }
}
