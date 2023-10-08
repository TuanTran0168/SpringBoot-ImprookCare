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
public class AddMedicineDTO {

    @NotBlank
    private String medicineName;

    @NotBlank
    private String description;

    @NotBlank
    private String ingredients;

    @NotBlank
    private String dosage;

    @NotBlank
    private String unitPrice;

    @NotBlank
    private String medicineCategoryId;

}
