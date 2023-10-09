/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Administrator
 */
@Getter
@Setter
public class AddPrescriptionDTO {

    @NotBlank
    private String diagnosis;

    @NotBlank
    private String symptom;
    
    @NotBlank
    private String servicePrice;
    
    @NotBlank
    private String bookingId;
}
