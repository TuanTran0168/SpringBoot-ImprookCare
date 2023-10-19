/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.service.Impl;

import com.tuantran.IMPROOK_CARE.Specifications.GenericSpecifications;
import com.tuantran.IMPROOK_CARE.components.cloudinary.CloudinaryComponent;
import com.tuantran.IMPROOK_CARE.dto.AddMessageDTO;
import com.tuantran.IMPROOK_CARE.models.Message;
import com.tuantran.IMPROOK_CARE.models.ProfileDoctor;
import com.tuantran.IMPROOK_CARE.models.User;
import com.tuantran.IMPROOK_CARE.repository.MessageRepository;
import com.tuantran.IMPROOK_CARE.repository.ProfileDoctorRepository;
import com.tuantran.IMPROOK_CARE.repository.UserRepository;
import com.tuantran.IMPROOK_CARE.service.MessageService;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Administrator
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileDoctorRepository profileDoctorRepository;

    @Autowired
    private CloudinaryComponent cloudinaryComponent;

    @Autowired
    private Environment environment;

    @Override
    public Page<Message> getMessagesBetweenUsersAndProfileDoctors(Map<String, String> params) {
        try {
            String pageNumber = params.get("pageNumber");
            String userId = params.get("userId");
            String profileDoctorId = params.get("profileDoctorId");

            Sort mySort = Sort.by("createdDate").descending();

            int defaultPageNumber = 0;

            Pageable page = PageRequest.of(defaultPageNumber, Integer.parseInt(this.environment.getProperty("spring.data.web.pageable.default-page-size")), mySort);

            if (pageNumber != null && !pageNumber.isEmpty()) {
                if (!pageNumber.equals("NaN")) {
                    page = PageRequest.of(Integer.parseInt(pageNumber), Integer.parseInt(this.environment.getProperty("spring.data.web.pageable.default-page-size")), mySort);
                }
            }

            return this.messageRepository.getMessagesBetweenUsersAndProfileDoctors(Integer.parseInt(userId), Integer.parseInt(profileDoctorId), page);

        } catch (NumberFormatException ex) {
            System.out.println("Lỗi parse số rồi má ơi: " + ex);
            return null;
        } catch (NullPointerException ex) {
            System.out.println("params null: " + ex);
            return null;
        }
    }

    @Override
    public int addMessage(AddMessageDTO addMessageDTO, MultipartFile avatar) {
        try {
            Message message = new Message();

            Optional<User> userOptional = this.userRepository.findUserByUserIdAndActiveTrue(Integer.parseInt(addMessageDTO.getUserId()));

            if (!userOptional.isPresent()) {
                return 0;
            }

            message.setUserId(userOptional.get());

            Optional<ProfileDoctor> profileDoctorOptional = this.profileDoctorRepository.findProfileDoctorByProfileDoctorIdAndActiveTrue(Integer.parseInt(addMessageDTO.getProfileDoctorId()));

            if (!profileDoctorOptional.isPresent()) {
                return 0;
            }

            message.setProfileDoctorId(profileDoctorOptional.get());

            if (avatar != null && !avatar.isEmpty()) {
                String linkCloudinaryAvatar = cloudinaryComponent.Cloudinary(avatar).get("secure_url").toString();
                message.setAvatar(linkCloudinaryAvatar);
            }

            message.setSenderId(Integer.parseInt(addMessageDTO.getSenderId()));

            message.setMessageContent(addMessageDTO.getMessageContent());
            message.setActive(Boolean.TRUE);
            message.setCreatedDate(new Date());

            this.messageRepository.save(message);
            return 1;
        } catch (NumberFormatException | NoSuchElementException ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    @Override
    public Page<Message> findMessagesByUserIdAndProfileDoctorIdPage(int userId, int profileDoctorId, Map<String, String> params) {

        String pageNumber = params.get("pageNumber");

        Optional<User> userOptional = this.userRepository.findUserByUserIdAndActiveTrue(userId);

        if (!userOptional.isPresent()) {
        }

        Optional<ProfileDoctor> profileDoctorOptional = this.profileDoctorRepository.findProfileDoctorByProfileDoctorIdAndActiveTrue(profileDoctorId);

        if (!profileDoctorOptional.isPresent()) {
        }

        int defaultPageNumber = 0;
        Sort mySort = Sort.by("createdDate").descending();
        Pageable page = PageRequest.of(defaultPageNumber, Integer.parseInt(this.environment.getProperty("spring.data.web.pageable.default-page-size")), mySort);

        if (pageNumber != null && !pageNumber.isEmpty()) {
            if (!pageNumber.equals("NaN")) {
                page = PageRequest.of(Integer.parseInt(pageNumber), Integer.parseInt(this.environment.getProperty("spring.data.web.pageable.default-page-size")), mySort);
            }
        }

        return this.messageRepository.findMessagesByUserIdAndProfileDoctorId(userOptional.get(), profileDoctorOptional.get(), page);

    }

    @Override
    public Page<Object[]> getAllUsersByProfileDoctorMessaging(int profileDoctorId, Map<String, String> params) {
        String pageNumber = params.get("pageNumber");

        Optional<ProfileDoctor> profileDoctorOptional = this.profileDoctorRepository.findProfileDoctorByProfileDoctorIdAndActiveTrue(profileDoctorId);

        if (!profileDoctorOptional.isPresent()) {
        }

        int defaultPageNumber = 0;
//        Sort mySort = Sort.by("createdDate").descending();
        Pageable page = PageRequest.of(defaultPageNumber, Integer.parseInt(this.environment.getProperty("spring.data.web.pageable.default-page-size")));

        if (pageNumber != null && !pageNumber.isEmpty()) {
            if (!pageNumber.equals("NaN")) {
                page = PageRequest.of(Integer.parseInt(pageNumber), Integer.parseInt(this.environment.getProperty("spring.data.web.pageable.default-page-size")));
            }
        }

        /* ĐÃ DISTINCT BÊN KIA THÌ KHÔNG SORT BÊN NÀY :) KHÔNG LỖI RÁNG CHỊU :)
            java.sql.SQLSyntaxErrorException: Expression #1 of ORDER BY clause is not in GROUP BY clause and contains nonaggregated column 'improokcare.m1_0.created_date' which is not functionally dependent on columns in GROUP BY clause; this is incompatible with sql_mode=only_full_group_by
         */
//        Pageable page = PageRequest.of(defaultPageNumber, Integer.parseInt(this.environment.getProperty("spring.data.web.pageable.default-page-size")));
        return this.messageRepository.getAllUsersByProfileDoctorMessaging(profileDoctorId, page);
    }

    @Override
    public List<Message> findMessagesByUserIdAndProfileDoctorId(int userId, int profileDoctorId) {

        Optional<User> userOptional = this.userRepository.findUserByUserIdAndActiveTrue(userId);

        if (!userOptional.isPresent()) {
        }

        Optional<ProfileDoctor> profileDoctorOptional = this.profileDoctorRepository.findProfileDoctorByProfileDoctorIdAndActiveTrue(profileDoctorId);

        if (!profileDoctorOptional.isPresent()) {
        }

        List<Specification<Message>> listSpec = new ArrayList<>();

        listSpec.add(GenericSpecifications.orderByAscending("createdDate"));
        listSpec.add(GenericSpecifications.fieldEquals("profileDoctorId", profileDoctorOptional.get()));
        listSpec.add(GenericSpecifications.fieldEquals("userId", userOptional.get()));

        return this.messageRepository.findAll(GenericSpecifications.createSpecification(listSpec));
    }

}
