/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.dto;

import java.util.Date;
import java.util.List;
import lombok.Data;

/**
 *
 * @author Administrator
 */

@Data
public class SendCustomEmailDTO {
    private String mailTo;
    private String mailSubject;
    private String mailContent;

    public Date getMailSendDate() {
        return new Date();
    }
}
