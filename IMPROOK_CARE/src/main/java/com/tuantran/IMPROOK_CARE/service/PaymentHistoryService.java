/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tuantran.IMPROOK_CARE.models.Booking;
import com.tuantran.IMPROOK_CARE.models.PaymentHistory;

/**
 *
 * @author Administrator
 */
public interface PaymentHistoryService {
        Page<?> findPaymentHistoryByProfilePatientId(int profilePatientId, Pageable page);

        PaymentHistory addPaymentHistory(PaymentHistory paymentHistory, Booking booking);

        Optional<PaymentHistory> findByPaymentHistoryId(Integer paymentHistoryId);

        Optional<PaymentHistory> findPaymentHistoryByBookingId(Booking bookingId);
}
