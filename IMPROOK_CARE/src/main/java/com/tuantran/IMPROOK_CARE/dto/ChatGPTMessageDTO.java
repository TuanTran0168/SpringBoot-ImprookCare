/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Administrator
 */

@Getter
@Setter
@NoArgsConstructor
public class ChatGPTMessageDTO {
    private String role;
    private String content;

    public ChatGPTMessageDTO(String user, String prompt) {
        this.role = user;
        this.content = prompt;
    }
}
