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
public class UpdateProfilePatientDTO {

    @NotBlank
    String profilePatientId;
    @NotBlank
    String name;
    @NotBlank
    String phonenumber;
    @NotBlank
    String provinceName;
    @NotBlank
    String districtName;
    @NotBlank
    String wardName;
    @NotBlank
    String persionalAddress;
//    @NotBlank
//    String address;
    @NotBlank
    String email;
    @NotBlank
    String relationship;
}
