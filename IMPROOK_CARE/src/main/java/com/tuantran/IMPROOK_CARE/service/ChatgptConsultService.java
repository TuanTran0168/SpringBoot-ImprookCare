/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.service;

import com.tuantran.IMPROOK_CARE.dto.AddCommentDTO;
import com.tuantran.IMPROOK_CARE.dto.UpdateCommentDTO;
import com.tuantran.IMPROOK_CARE.models.ChatgptConsult;
import com.tuantran.IMPROOK_CARE.models.ChatgptQuestion;
import com.tuantran.IMPROOK_CARE.models.Comment;
import com.tuantran.IMPROOK_CARE.models.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Administrator
 */
public interface ChatgptConsultService {

    Page<ChatgptConsult> findByUserIdOrderByCreatedDateDesc(User userId, Pageable page);

    Optional<ChatgptConsult> findByChatgptConsultId(Integer chatgptConsultId);

    Optional<ChatgptConsult> findByChatgptQuestionId(ChatgptQuestion chatgptQuestionId);

    ChatgptConsult addChatgptConsult(ChatgptConsult chatgptConsult);
}
