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
public class UpdateUserForUserDTO {

    @NotBlank
    private String userId;
    @NotBlank
    private String firstname;
    @NotBlank
    private String lastname;
    private Boolean gender;
    @NotBlank
    private String birthday;
}
