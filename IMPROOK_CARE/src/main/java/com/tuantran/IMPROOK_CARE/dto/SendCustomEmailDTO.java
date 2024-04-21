/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.Date;
import lombok.Data;

/**
 *
 * @author Administrator
 */
@Data
public class SendCustomEmailDTO {

    @NotBlank
    private String mailTo;
    @NotBlank
    private String mailSubject;
    @NotBlank
    private String mailContent;

    public Date getMailSendDate() {
        return new Date();
    }
}
