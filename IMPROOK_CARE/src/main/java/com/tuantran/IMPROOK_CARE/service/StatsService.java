/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.service;

import java.util.List;

/**
 *
 * @author Administrator
 */
public interface StatsService {

    List<Object[]> statsBookingByUser();

    List<Object[]> statsServicePricePaid();

    List<Object[]> statsServicePriceUnpaid();
    
    List<Object[]> statsServicePriceAllpaid();
    
    List<Object[]> statsMedicinePrescriptionPaid();

    List<Object[]> statsMedicinePrescriptionUnpaid();

    List<Object[]> statsMedicinePrescriptionAllPaid();
}
