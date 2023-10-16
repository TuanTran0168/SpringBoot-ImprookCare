/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.service.Impl;

import com.tuantran.IMPROOK_CARE.repository.StatsRepository;
import com.tuantran.IMPROOK_CARE.service.StatsService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Administrator
 */
@Service
public class StatsServiceImpl implements StatsService {

    @Autowired
    private StatsRepository statsRepository;
    
    @Override
    public List<Object[]> statsBookingByUser() {
        return this.statsRepository.statsBookingByUser();
    }

    @Override
    public List<Object[]> statsServicePricePaid() {
        return this.statsRepository.statsServicePricePaid();
    }

    @Override
    public List<Object[]> statsServicePriceUnpaid() {
        return this.statsRepository.statsServicePriceUnpaid();
    }

    @Override
    public List<Object[]> statsServicePriceAllpaid() {
        return this.statsRepository.statsServicePriceAllpaid();
    }

    @Override
    public List<Object[]> statsMedicinePrescriptionPaid() {
        return this.statsRepository.statsMedicinePrescriptionPaid();
    }

    @Override
    public List<Object[]> statsMedicinePrescriptionUnpaid() {
        return this.statsRepository.statsMedicinePrescriptionUnpaid();
    }

    @Override
    public List<Object[]> statsMedicinePrescriptionAllPaid() {
        return this.statsRepository.statsMedicinePrescriptionAllPaid();
    }

}
