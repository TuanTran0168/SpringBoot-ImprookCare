/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.controllers;

import com.tuantran.IMPROOK_CARE.dto.BookingDTO;
import com.tuantran.IMPROOK_CARE.service.BookingService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
}
