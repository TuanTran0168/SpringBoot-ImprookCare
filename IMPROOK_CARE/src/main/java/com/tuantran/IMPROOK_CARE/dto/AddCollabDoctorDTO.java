/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 *
 * @author Administrator
 */

@Data
public class AddCollabDoctorDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String phonenumber;

    @NotBlank
    private String email;
}
