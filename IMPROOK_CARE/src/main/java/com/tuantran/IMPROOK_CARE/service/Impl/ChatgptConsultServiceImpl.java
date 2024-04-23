/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.service.Impl;

import com.tuantran.IMPROOK_CARE.models.ChatgptConsult;
import com.tuantran.IMPROOK_CARE.models.ChatgptQuestion;
import com.tuantran.IMPROOK_CARE.models.User;
import com.tuantran.IMPROOK_CARE.repository.ChatgptConsultRepository;
import com.tuantran.IMPROOK_CARE.service.ChatgptConsultService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author Administrator
 */
@Service
public class ChatgptConsultServiceImpl implements ChatgptConsultService {

    @Autowired
    private ChatgptConsultRepository chatgptConsultRepository;

    @Override
    public Page<ChatgptConsult> findByUserIdOrderByCreatedDateDesc(User userId, Pageable page) {
        return this.chatgptConsultRepository.findByUserIdOrderByCreatedDateDesc(userId, page);
    }

    @Override
    public Optional<ChatgptConsult> findByChatgptConsultId(Integer chatgptConsultId) {
        return this.chatgptConsultRepository.findByChatgptConsultId(chatgptConsultId);
    }

    @Override
    public Optional<ChatgptConsult> findByChatgptQuestionId(ChatgptQuestion chatgptQuestionId) {
        return this.chatgptConsultRepository.findByChatgptQuestionId(chatgptQuestionId);
    }

    @Override
    public ChatgptConsult addChatgptConsult(ChatgptConsult chatgptConsult) {
        return this.chatgptConsultRepository.save(chatgptConsult);
    }

}
