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
public class UpdateProfileDoctorDTO {

    @NotBlank
    private String profileDoctorId;
    @NotBlank
    private String name;
    @NotBlank
    private String phonenumber;
    @NotBlank
    private String email;
    @NotBlank
    private String workPlace;
    @NotBlank
    private String position;
    @NotBlank
    private String specialtyId;
}
