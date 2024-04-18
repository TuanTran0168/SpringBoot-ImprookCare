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
    public List<?> statsBookingByUser() {
        return this.statsRepository.statsBookingByUser();
    }

    @Override
    public List<?> statsServicePricePaid() {
        return this.statsRepository.statsServicePricePaid();
    }

    @Override
    public List<?> statsServicePriceUnpaid() {
        return this.statsRepository.statsServicePriceUnpaid();
    }

    @Override
    public List<?> statsServicePriceAllpaid() {
        return this.statsRepository.statsServicePriceAllpaid();
    }

    @Override
    public List<?> statsMedicinePrescriptionPaid() {
        return this.statsRepository.statsMedicinePrescriptionPaid();
    }

    @Override
    public List<?> statsMedicinePrescriptionUnpaid() {
        return this.statsRepository.statsMedicinePrescriptionUnpaid();
    }

    @Override
    public List<?> statsMedicinePrescriptionAllPaid() {
        return this.statsRepository.statsMedicinePrescriptionAllPaid();
    }

    @Override
    public List<?> statsCountMedicineAllPaid() {
        return this.statsRepository.statsCountMedicineAllPaid();
    }

    @Override
    public List<?> statsCountMedicinePaid() {
        return this.statsRepository.statsCountMedicinePaid();
    }

    @Override
    public List<?> statsCountMedicineUnpaid() {
        return this.statsRepository.statsCountMedicineUnpaid();
    }

    @Override
    public List<?> statsRevenueMedicineAllpaid() {
        return this.statsRepository.statsRevenueMedicineAllpaid();
    }

    @Override
    public List<?> statsRevenueMedicinePaid() {
        return this.statsRepository.statsRevenueMedicinePaid();
    }

    @Override
    public List<?> statsRevenueMedicineUnpaid() {
        return this.statsRepository.statsRevenueMedicineUnpaid();
    }

}
