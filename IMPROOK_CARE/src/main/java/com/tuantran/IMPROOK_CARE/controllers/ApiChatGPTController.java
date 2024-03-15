/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.controllers;

import com.tuantran.IMPROOK_CARE.configs.chatGPT.ChatGPTConfig;
import com.tuantran.IMPROOK_CARE.dto.ChatGPTResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Administrator
 */

@RestController
@RequestMapping("/api")
public class ApiChatGPTController {
    @Autowired
    private ChatGPTConfig chatGPTConfig;
    
    @GetMapping("/public/chatbot")
    public String chatBot(@RequestParam("query") String query) {
            ChatGPTResponseDTO response = chatGPTConfig.getChatGPTRespone(query);

            return response.getChoices().get(0).getMessage().getContent();
    }
}
