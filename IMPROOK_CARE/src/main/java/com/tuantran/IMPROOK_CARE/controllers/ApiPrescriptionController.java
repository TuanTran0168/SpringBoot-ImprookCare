/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.controllers;

import com.tuantran.IMPROOK_CARE.dto.AddPrescriptionAndDetailsDTO;
import com.tuantran.IMPROOK_CARE.dto.AddPrescriptionDTO;
import com.tuantran.IMPROOK_CARE.dto.AddPrescriptionDetailDTO;
import com.tuantran.IMPROOK_CARE.models.Booking;
import com.tuantran.IMPROOK_CARE.service.BookingService;
import com.tuantran.IMPROOK_CARE.service.PrescriptionService;

import jakarta.persistence.NonUniqueResultException;

import java.util.Map;
import java.util.Optional;

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
public class ApiPrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;

    @Autowired
    private BookingService bookingService;

    // @PostMapping("/auth/doctor/add-prescription/")
    // @CrossOrigin
    // public ResponseEntity<String> addPrescription(@Valid @RequestBody
    // AddPrescriptionDTO addPrescriptionDTO, @Valid @RequestBody Map<String,
    // AddPrescriptionDetailDTO> prescriptionDetailDTO) {
    // String message = "Có lỗi xảy ra!";
    // int check = this.prescriptionService.addPrescription(addPrescriptionDTO,
    // prescriptionDetailDTO);
    //
    // if (check == 1) {
    // message = "Thêm đơn thuốc thành công!";
    // return new ResponseEntity<>(message, HttpStatus.OK);
    // } else if (check == 0) {
    // message = "Đơn thuốc thất bại!";
    // }
    //
    // return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    // }
    @PostMapping("/auth/doctor/add-prescription/")
    // @PostMapping(path = "/auth/doctor/add-prescription/",
    // consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
    // produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<String> addPrescription(
            @RequestBody AddPrescriptionAndDetailsDTO addPrescriptionAndDetailsDTO) {
        String message = "Có lỗi xảy ra!";
        AddPrescriptionDTO addPrescriptionDTO = addPrescriptionAndDetailsDTO.getAddPrescriptionDTO();
        Map<String, AddPrescriptionDetailDTO> prescriptionDetailDTO = addPrescriptionAndDetailsDTO
                .getPrescriptionDetailDTO();
        int check = this.prescriptionService.addPrescription(addPrescriptionDTO, prescriptionDetailDTO);

        if (check == 1) {
            message = "Thêm đơn thuốc thành công!";
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else if (check == 0) {
            message = "Thêm đơn thuốc thất bại!";
        }

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/auth/search-prescriptions/")
    @CrossOrigin
    public ResponseEntity<?> listSearchPrescriptions(@RequestParam Map<String, String> params) {
        return new ResponseEntity<>(this.prescriptionService.getPrescriptionsByProfilePatientIdPageSpec(params),
                HttpStatus.OK);
    }

    @PostMapping("/auth/pay-medicine/")
    @CrossOrigin
    public ResponseEntity<?> payMedicine(@RequestBody Map<String, String> params) {
        String message = "Có lỗi xảy ra!";

        String prescriptionId = params.get("prescriptionId");
        String medicine_payment_TxnRef = params.get("medicine_payment_TxnRef");

        int check = this.prescriptionService.payMedicine(Integer.parseInt(prescriptionId), medicine_payment_TxnRef);

        if (check == 1) {
            message = "Thanh toán thuốc thành công!";
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else if (check == 0) {
            message = "Thanh toán thuốc thất bại!";
        }

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/auth/pay-service/")
    @CrossOrigin
    public ResponseEntity<?> payService(@RequestBody Map<String, String> params) {
        String message = "Có lỗi xảy ra!";

        String prescriptionId = params.get("prescriptionId");
        String service_payment_TxnRef = params.get("service_payment_TxnRef");

        int check = this.prescriptionService.payService(Integer.parseInt(prescriptionId), service_payment_TxnRef);

        if (check == 1) {
            message = "Thanh toán tiền khám thành công!";
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else if (check == 0) {
            message = "Thanh toán tiền khám thất bại!";
        }

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/auth/booking/{bookingId}/prescription/")
    @CrossOrigin
    public ResponseEntity<?> findByBookingId(@PathVariable(value = "bookingId") int bookingId) {

        String message = "Có lỗi xảy ra!";

        Optional<Booking> bookingOptional = this.bookingService.findBookingByBookingIdAndActiveTrue(bookingId);
        if (bookingOptional.isPresent()) {
            try {
                return new ResponseEntity<>(this.prescriptionService.findByBookingId(bookingOptional.get()),
                        HttpStatus.OK);
            } catch (NonUniqueResultException ex) {
                ex.printStackTrace();
                message = "Lỗi nhất quán dữ liệu Booking[" + bookingId + "] - " + ex;
                return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            message = "Booking[" + bookingId + "] không tồn tại!";
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }

    }
}
