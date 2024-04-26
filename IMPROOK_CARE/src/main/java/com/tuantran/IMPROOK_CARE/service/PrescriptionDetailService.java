/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.service;

import com.tuantran.IMPROOK_CARE.models.PrescriptionDetail;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Administrator
 */
public interface PrescriptionDetailService {

    List<PrescriptionDetail> findPrescriptionDetailByPrescriptionId(int prescriptionId);

    Optional<PrescriptionDetail> findPrescriptionDetailByPrescriptionDetailIdAndActiveTrue(int prescriptionDetailId);
}
