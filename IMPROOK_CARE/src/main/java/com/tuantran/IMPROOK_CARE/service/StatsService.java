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
    List<?> statsBookingByUser();

    // Thống kê tiền khám dịch vụ từ bệnh nhân theo bác sĩ (bác sĩ này đã khám được
    // bao nhiêu tiền)
    List<?> statsServicePricePaid();

    List<?> statsServicePriceUnpaid();

    List<?> statsServicePriceAllpaid();

    // Thống kê tiền thuốc từ bệnh nhân theo bác sĩ (bác sĩ này kê được bao nhiêu
    // thuốc)
    List<?> statsMedicinePrescriptionPaid();

    List<?> statsMedicinePrescriptionUnpaid();

    List<?> statsMedicinePrescriptionAllPaid();

    // Thống kê số lượng thuốc đã kê từ hệ thống (đã bán)
    List<?> statsCountMedicineAllPaid();

    List<?> statsCountMedicinePaid();

    List<?> statsCountMedicineUnpaid();

    // Thống kê số tiền thuốc đã kê từ hệ thống (đã bán)
    List<?> statsRevenueMedicineAllpaid();

    List<?> statsRevenueMedicinePaid();

    List<?> statsRevenueMedicineUnpaid();
}
