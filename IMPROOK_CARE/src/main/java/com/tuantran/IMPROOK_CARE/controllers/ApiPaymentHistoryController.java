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

        // {
        // "vnp_ResponseId": "b8fdbc7a032349e7bdaf23432fe89db7",
        // "vnp_Command": "querydr",
        // "vnp_ResponseCode": "00",
        // "vnp_Message": "QueryDR Success",
        // "vnp_TmnCode": "86LMDA46",
        // "vnp_TxnRef": "45242740",
        // "vnp_Amount": "1000000",
        // "vnp_OrderInfo": "Tuan Tran test VN pay-45242740",
        // "vnp_BankCode": "NCB",
        // "vnp_PayDate": "20240423093740",
        // "vnp_TransactionNo": "14388500",
        // "vnp_TransactionType": "01",
        // "vnp_TransactionStatus": "00",
        // "vnp_SecureHash":
        // "df9fdb8c74201cebaf8d0e617785afcedf1ae900821932f72f8fc725aa2516735eaccf1f33aa1c30c74fc4a2b0980c46d38aa3d07eb1f1dcdb6568797fb092a8"
        // }

        String bookingId = params.get("bookingId");
        String vnp_ResponseId = params.get("vnp_ResponseId");
        String vnp_command = params.get("vnp_command");
        String vnp_ResponseCode = params.get("vnp_ResponseCode");
        String vnp_Message = params.get("vnp_Message");
        String vnp_tmncode = params.get("vnp_tmncode");
        String vnp_txnref = params.get("vnp_txnref");
        String vnp_amount = params.get("vnp_amount");
        String vnp_orderinfo = params.get("vnp_orderinfo");
        String vnp_bankcode = params.get("vnp_bankcode");
        String vnp_PayDate = params.get("vnp_PayDate");
        String vnp_TransactionNo = params.get("vnp_TransactionNo");
        String vnp_TransactionStatus = params.get("vnp_TransactionStatus");
        String vnp_securehash = params.get("vnp_securehash");

        Optional<Booking> bookingOptional = this.bookingService
                .findBookingByBookingIdAndActiveTrue(Integer.parseInt(bookingId));

        if (bookingOptional.isPresent()) {
            PaymentHistory paymentHistory = new PaymentHistory();
            paymentHistory.setActive(Boolean.TRUE);
            paymentHistory.setCreatedDate(new Date());

            paymentHistory.setVnpResponseid(vnp_ResponseId);
            paymentHistory.setVnpCommand(vnp_command);
            paymentHistory.setVnpResponsecode(vnp_ResponseCode);
            paymentHistory.setVnpMessage(vnp_Message);
            paymentHistory.setVnpTmncode(vnp_tmncode);
            paymentHistory.setVnpTxnref(vnp_txnref);
            paymentHistory.setVnpAmount(vnp_amount);
            paymentHistory.setVnpOrderinfo(vnp_orderinfo);
            paymentHistory.setVnpBankcode(vnp_bankcode);
            paymentHistory.setVnpPaydate(vnp_PayDate);
            paymentHistory.setVnpTransactionno(vnp_TransactionNo);
            paymentHistory.setVnpTransactionstatus(vnp_TransactionStatus);
            paymentHistory.setVnpSecurehash(vnp_securehash);
            paymentHistory.setBookingId(bookingOptional.get());
            return new ResponseEntity<>(this.paymentHistoryService.addPaymentHistory(paymentHistory),
                    HttpStatus.OK);
        } else {
            message = "Booking[" + bookingId + "] không tồn tại!";
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }

    }

}
