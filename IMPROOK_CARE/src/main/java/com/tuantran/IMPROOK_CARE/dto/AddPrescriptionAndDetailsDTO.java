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
public class AddPrescriptionAndDetailsDTO {
    private AddPrescriptionDTO addPrescriptionDTO;
    private Map<String, AddPrescriptionDetailDTO> prescriptionDetailDTO;
}
