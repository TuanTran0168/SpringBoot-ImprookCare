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
public class AddMessageSocketDTO {

    @NotBlank
    private String userId;

    @NotBlank
    private String profileDoctorId;

    @NotBlank
    private String senderId;

    @NotBlank
    private String messageContent;

    private String avatar;

    // @NotBlank
    private Boolean isSeen;
}
