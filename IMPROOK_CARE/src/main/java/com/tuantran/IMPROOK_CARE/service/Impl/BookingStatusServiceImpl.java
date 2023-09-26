/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.service.Impl;

import com.tuantran.IMPROOK_CARE.models.BookingStatus;
import com.tuantran.IMPROOK_CARE.repository.BookingStatusRepository;
import com.tuantran.IMPROOK_CARE.service.BookingStatusService;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Administrator
 */
public class BookingStatusServiceImpl implements BookingStatusService {

    @Autowired
    private BookingStatusRepository bookingStatusRepository;

    @Override
    public BookingStatus findBookingStatusByStatusId(int bookingStatusId) {
        try {
            Optional<BookingStatus> bookingStatusOptional = this.bookingStatusRepository.findBookingStatusByStatusId(bookingStatusId);
            if (bookingStatusOptional.isPresent()) {
                return bookingStatusOptional.get();
            } else {
                return null;
            }

        } catch (NoSuchElementException ex) {
            return null;
        }

    }

}
