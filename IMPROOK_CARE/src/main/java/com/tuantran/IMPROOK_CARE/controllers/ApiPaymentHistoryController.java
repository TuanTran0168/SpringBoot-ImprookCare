/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.controllers;

import com.tuantran.IMPROOK_CARE.models.Booking;
import com.tuantran.IMPROOK_CARE.models.PaymentHistory;
import com.tuantran.IMPROOK_CARE.models.ProfilePatient;
import com.tuantran.IMPROOK_CARE.service.BookingService;
import com.tuantran.IMPROOK_CARE.service.PaymentHistoryService;
import com.tuantran.IMPROOK_CARE.service.ProfilePatientService;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Administrator
 */
@RestController
@RequestMapping("/api")
public class ApiPaymentHistoryController {

    @Autowired
    private PaymentHistoryService paymentHistoryService;

    @Autowired
    private Environment environment;

    @Autowired
    private ProfilePatientService profilePatientService;

    @Autowired
    private BookingService bookingService;

    @GetMapping("/auth/profile-patient/{profilePatientId}/payment-history/")
    @CrossOrigin
    public ResponseEntity<?> findPaymentHistoryByProfilePatientId(
            @PathVariable(value = "profilePatientId") String profilePatientId,
            @RequestParam Map<String, String> params) {
        String message = "Có lỗi xảy ra!";

        Optional<ProfilePatient> profilePatientOptional = this.profilePatientService
                .findProfilePatientByProfilePatientIdAndActiveTrueOptional(Integer.parseInt(profilePatientId));
        if (profilePatientOptional.isPresent()) {
            String pageNumber = params.get("pageNumber");
            int defaultPageNumber = 0;
            Sort mySort = Sort.by("createdDate").descending();
            Pageable page = PageRequest.of(defaultPageNumber,
                    Integer.parseInt(this.environment.getProperty("spring.data.web.pageable.default-page-size")),
                    mySort);
            if (pageNumber != null && !pageNumber.isEmpty()) {
                if (!pageNumber.equals("NaN")) {
                    page = PageRequest.of(Integer.parseInt(pageNumber),
                            Integer.parseInt(
                                    this.environment.getProperty("spring.data.web.pageable.default-page-size")),
                            mySort);
                }
            }
            return ResponseEntity.ok()
                    .body(this.paymentHistoryService.findPaymentHistoryByProfilePatientId(
                            profilePatientOptional.get().getProfilePatientId(), page));
        } else {
            message = "ProfilePatient[" + profilePatientId + "] không tồn tại!";
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/auth/add-payment/")
    @CrossOrigin
    public ResponseEntity<?> listSearchMedicineCategories(@RequestParam Map<String, String> params) {
        String message = "Có lỗi xảy ra!";

        String paymentStatus = params.get("paymentStatus");
        String bookingId = params.get("bookingId");
        String vnp_amount = params.get("vnp_amount");
        String vnp_bankcode = params.get("vnp_bankcode");
        String vnp_command = params.get("vnp_command");
        String vnp_createdate = params.get("vnp_createdate");
        String vnp_currcode = params.get("vnp_currcode");
        String vnp_expiredate = params.get("vnp_expiredate");
        String vnp_ipaddr = params.get("vnp_ipaddr");
        String vnp_locale = params.get("vnp_locale");
        String vnp_orderinfo = params.get("vnp_orderinfo");
        String vnp_ordertype = params.get("vnp_ordertype");
        String vnp_returnurl = params.get("vnp_returnurl");
        String vnp_tmncode = params.get("vnp_tmncode");
        String vnp_txnref = params.get("vnp_txnref");
        String vnp_version = params.get("vnp_version");
        String vnp_securehash = params.get("vnp_securehash");

        Optional<Booking> bookingOptional = this.bookingService
                .findBookingByBookingIdAndActiveTrue(Integer.parseInt(bookingId));

        if (bookingOptional.isPresent()) {
            PaymentHistory paymentHistory = new PaymentHistory();
            paymentHistory.setActive(Boolean.TRUE);
            paymentHistory.setCreatedDate(new Date());
            paymentHistory.setVnpAmount(vnp_amount);
            paymentHistory.setVnpBankcode(vnp_bankcode);
            paymentHistory.setVnpCommand(vnp_command);
            paymentHistory.setVnpCreatedate(vnp_createdate);
            paymentHistory.setVnpCurrcode(vnp_currcode);
            paymentHistory.setVnpExpiredate(vnp_expiredate);
            paymentHistory.setVnpIpaddr(vnp_ipaddr);
            paymentHistory.setVnpLocale(vnp_locale);
            paymentHistory.setVnpOrderinfo(vnp_orderinfo);
            paymentHistory.setVnpOrdertype(vnp_ordertype);
            paymentHistory.setVnpReturnurl(vnp_returnurl);
            paymentHistory.setVnpTmncode(vnp_tmncode);
            paymentHistory.setVnpTxnref(vnp_txnref);
            paymentHistory.setVnpVersion(vnp_version);
            paymentHistory.setVnpSecurehash(vnp_securehash);

            paymentHistory.setPaymentStatus(Boolean.parseBoolean(paymentStatus));
            paymentHistory.setBookingId(bookingOptional.get());
            return new ResponseEntity<>(this.paymentHistoryService.addPaymentHistory(paymentHistory),
                    HttpStatus.OK);
        } else {
            message = "Booking[" + bookingId + "] không tồn tại!";
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }

    }

}
