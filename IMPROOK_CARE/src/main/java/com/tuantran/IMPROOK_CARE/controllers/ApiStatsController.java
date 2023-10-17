/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.controllers;

import com.tuantran.IMPROOK_CARE.service.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Administrator
 */
@RestController
@RequestMapping("/api")
public class ApiStatsController {

    @Autowired
    private StatsService statsService;

    @GetMapping("/public/test-stats/")
    @CrossOrigin
    public ResponseEntity<?> test() {
        return new ResponseEntity<>(this.statsService.statsRevenueMedicineAllpaid(), HttpStatus.OK);
    }

    @GetMapping("/public/stats-booking-by-user/")
    @CrossOrigin
    public ResponseEntity<?> statsBookingByUser() {
        return new ResponseEntity<>(this.statsService.statsBookingByUser(), HttpStatus.OK);
    }

    @GetMapping("/public/stats-service-price-paid/")
    @CrossOrigin
    public ResponseEntity<?> statsServicePricePaid() {
        return new ResponseEntity<>(this.statsService.statsServicePricePaid(), HttpStatus.OK);
    }

    @GetMapping("/public/stats-service-price-unpaid/")
    @CrossOrigin
    public ResponseEntity<?> statsServicePriceUnpaid() {
        return new ResponseEntity<>(this.statsService.statsServicePriceUnpaid(), HttpStatus.OK);
    }

    @GetMapping("/public/stats-service-price-allpaid/")
    @CrossOrigin
    public ResponseEntity<?> statsServicePriceAllpaid() {
        return new ResponseEntity<>(this.statsService.statsServicePriceAllpaid(), HttpStatus.OK);
    }

    @GetMapping("/public/stats-medicine-prescription-paid/")
    @CrossOrigin
    public ResponseEntity<?> statsMedicinePrescriptionPaid() {
        return new ResponseEntity<>(this.statsService.statsMedicinePrescriptionPaid(), HttpStatus.OK);
    }

    @GetMapping("/public/stats-medicine-prescription-unpaid/")
    @CrossOrigin
    public ResponseEntity<?> statsMedicinePrescriptionUnpaid() {
        return new ResponseEntity<>(this.statsService.statsMedicinePrescriptionUnpaid(), HttpStatus.OK);
    }

    @GetMapping("/public/stats-medicine-prescription-allpaid/")
    @CrossOrigin
    public ResponseEntity<?> statsMedicinePrescriptionAllpaid() {
        return new ResponseEntity<>(this.statsService.statsMedicinePrescriptionAllPaid(), HttpStatus.OK);
    }

    @GetMapping("/public/stats-count-medicine-allpaid/")
    @CrossOrigin
    public ResponseEntity<?> statsCountMedicineAllPaid() {
        return new ResponseEntity<>(this.statsService.statsCountMedicineAllPaid(), HttpStatus.OK);
    }

    @GetMapping("/public/stats-count-medicine-paid/")
    @CrossOrigin
    public ResponseEntity<?> statsCountMedicinePaid() {
        return new ResponseEntity<>(this.statsService.statsCountMedicinePaid(), HttpStatus.OK);
    }

    @GetMapping("/public/stats-count-medicine-unpaid/")
    @CrossOrigin
    public ResponseEntity<?> statsCountMedicineUnpaid() {
        return new ResponseEntity<>(this.statsService.statsCountMedicineUnpaid(), HttpStatus.OK);
    }

    //====================
    @GetMapping("/public/stats-revenue-medicine-allpaid/")
    @CrossOrigin
    public ResponseEntity<?> statsRevenueMedicineAllpaid() {
        return new ResponseEntity<>(this.statsService.statsRevenueMedicineAllpaid(), HttpStatus.OK);
    }

    @GetMapping("/public/stats-revenue-medicine-paid/")
    @CrossOrigin
    public ResponseEntity<?> statsRevenueMedicinepaid() {
        return new ResponseEntity<>(this.statsService.statsRevenueMedicinePaid(), HttpStatus.OK);
    }
    
    @GetMapping("/public/stats-revenue-medicine-unpaid/")
    @CrossOrigin
    public ResponseEntity<?> statsRevenueMedicineUnpaid() {
        return new ResponseEntity<>(this.statsService.statsRevenueMedicineUnpaid(), HttpStatus.OK);
    }
}
