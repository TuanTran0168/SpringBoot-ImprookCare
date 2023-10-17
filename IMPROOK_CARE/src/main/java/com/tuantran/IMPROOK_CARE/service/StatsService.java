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

    // Số lượng Booking của mỗi User (bác sĩ)
    List<Object[]> statsBookingByUser();

    // Thống kê tiền khám dịch vụ từ bệnh nhân theo bác sĩ (bác sĩ này đã khám được bao nhiêu tiền)
    List<Object[]> statsServicePricePaid();

    List<Object[]> statsServicePriceUnpaid();

    List<Object[]> statsServicePriceAllpaid();

    // Thống kê tiền thuốc từ bệnh nhân theo bác sĩ (bác sĩ này kê được bao nhiêu thuốc)
    List<Object[]> statsMedicinePrescriptionPaid();

    List<Object[]> statsMedicinePrescriptionUnpaid();

    List<Object[]> statsMedicinePrescriptionAllPaid();

    // Thống kê số lượng thuốc đã kê từ hệ thống (đã bán)
    List<Object[]> statsCountMedicineAllPaid();

    List<Object[]> statsCountMedicinePaid();

    List<Object[]> statsCountMedicineUnpaid();

    // Thống kê số tiền thuốc đã kê từ hệ thống (đã bán)
    List<Object[]> statsRevenueMedicineAllpaid();

    List<Object[]> statsRevenueMedicinePaid();

    List<Object[]> statsRevenueMedicineUnpaid();
}
