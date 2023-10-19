/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.service;

import com.tuantran.IMPROOK_CARE.dto.AddMessageDTO;
import com.tuantran.IMPROOK_CARE.models.Message;
import com.tuantran.IMPROOK_CARE.models.ProfileDoctor;
import com.tuantran.IMPROOK_CARE.models.User;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Administrator
 */
public interface MessageService {

    Page<Message> getMessagesBetweenUsersAndProfileDoctors(Map<String, String> params);

    Page<Message> findMessagesByUserIdAndProfileDoctorIdPage(int userId, int profileDoctorId, Map<String, String> params);

    List<Message> findMessagesByUserIdAndProfileDoctorId(int userId, int profileDoctorId);

    int addMessage(AddMessageDTO addMessageDTO, MultipartFile avatar);

    Page<Object[]> getAllUsersByProfileDoctorMessaging(int profileDoctorId, Map<String, String> params);
}
