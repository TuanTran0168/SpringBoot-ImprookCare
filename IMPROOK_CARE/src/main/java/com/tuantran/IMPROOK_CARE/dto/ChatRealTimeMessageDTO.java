/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Administrator
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ChatRealTimeMessageDTO {

    private String senderName;
    private String receiverName;
    private String message;
    private String date;
    private ChatRealTimeStatusMessageDTO status;
}
