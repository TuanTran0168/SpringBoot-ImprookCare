/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.service;

import com.tuantran.IMPROOK_CARE.dto.AddPrescriptionDTO;
import com.tuantran.IMPROOK_CARE.dto.AddPrescriptionDetailDTO;
import java.util.Map;

/**
 *
 * @author Administrator
 */
public interface PrescriptionService {
    int addPrescription(AddPrescriptionDTO addPrescriptionDTO, Map<String, AddPrescriptionDetailDTO> prescriptionDetailDTO);
}
