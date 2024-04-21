/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.configs.chatGPT;

import com.tuantran.IMPROOK_CARE.dto.ChatGPTMessageDTO;
import com.tuantran.IMPROOK_CARE.dto.ChatGPTRequestDTO;
import com.tuantran.IMPROOK_CARE.dto.ChatGPTResponseDTO;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Administrator
 */

@Configuration
public class ChatGPTConfig {
    @Value("${openai.api.key}")
    private String apiKey;

    private static final String OPEN_API_CHAT_ENDPOINT = "https://api.openai.com/v1/chat/completions";

    public ChatGPTResponseDTO getChatGPTRespone(String prompt) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + apiKey);

            ChatGPTRequestDTO request = new ChatGPTRequestDTO();
            request.setModel("gpt-3.5-turbo");
            request.setMessages(List.of(new ChatGPTMessageDTO("user", prompt)));

            HttpEntity<ChatGPTRequestDTO> entity = new HttpEntity<>(request, headers);

            RestTemplate template = new RestTemplate();
            return template.postForObject(OPEN_API_CHAT_ENDPOINT, entity, ChatGPTResponseDTO.class);
    }
}
