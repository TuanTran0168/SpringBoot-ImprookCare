/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Administrator
 */

@Getter
@Setter
public class ChatGPTResponseDTO {
    private List<Choice> choices;
    
    @Getter
    @Setter
    public static class Choice {
		private int index;
		private ChatGPTMessageDTO message;
    }
}
