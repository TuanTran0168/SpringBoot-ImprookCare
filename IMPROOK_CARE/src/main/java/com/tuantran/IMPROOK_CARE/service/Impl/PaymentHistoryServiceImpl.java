/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.service.Impl;

import com.tuantran.IMPROOK_CARE.models.Booking;
import com.tuantran.IMPROOK_CARE.models.PaymentHistory;
import com.tuantran.IMPROOK_CARE.repository.BookingRepository;
import com.tuantran.IMPROOK_CARE.repository.PaymentHistoryRepository;
import com.tuantran.IMPROOK_CARE.service.PaymentHistoryService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author Administrator
 */
@Service
public class PaymentHistoryServiceImpl implements PaymentHistoryService {

    @Autowired
    private PaymentHistoryRepository paymentHistoryRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public Page<?> findPaymentHistoryByProfilePatientId(int profilePatientId, Pageable page) {
        return this.paymentHistoryRepository.findPaymentHistoryByProfilePatientId(profilePatientId, page);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public PaymentHistory addPaymentHistory(PaymentHistory paymentHistory, Booking booking) {
        this.bookingRepository.save(booking);
        return this.paymentHistoryRepository.save(paymentHistory);
    }

}
