/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.dto;

import java.util.Map;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Administrator
 */

@Getter
@Setter
public class UpdatePrescriptionAndDetailsDTO {
    private UpdatePrescriptionDTO updatePrescriptionDTO;
    private Map<String, AddPrescriptionDetailDTO> prescriptionDetailDTO;
}
