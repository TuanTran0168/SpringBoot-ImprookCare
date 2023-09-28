/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.service;

import com.tuantran.IMPROOK_CARE.dto.BookingDTO;
import com.tuantran.IMPROOK_CARE.models.Booking;
import java.util.List;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Administrator
 */
public interface BookingService {

    int addBooking(BookingDTO bookingDTO);

    int acceiptBooking(int bookingId);
    
    int denyBooking(int bookingId);
    
//    List<Booking> findBookingForUserView(int userId);
    
    List<Object[]> getBookingForUserView(@Param("userId") int userId);
}
