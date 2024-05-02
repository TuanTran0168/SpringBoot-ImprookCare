/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.controllers;

import com.tuantran.IMPROOK_CARE.configs.chatGPT.ChatGPTConfig;
import com.tuantran.IMPROOK_CARE.dto.AddChatgptConsultDTO;
import com.tuantran.IMPROOK_CARE.dto.ChatGPTResponseDTO;
import com.tuantran.IMPROOK_CARE.models.ChatgptConsult;
import com.tuantran.IMPROOK_CARE.models.User;
import com.tuantran.IMPROOK_CARE.service.ChatgptConsultService;
import com.tuantran.IMPROOK_CARE.service.UserService;

import jakarta.validation.Valid;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @Autowired
    private UserService userService;

    @Autowired
    private ChatgptConsultService chatgptConsultService;

    @Autowired
    private Environment environment;

    private String MY_PROMPT = "Bạn là một nhân viên y tế và bạn chỉ trả lời các câu hỏi sau đây liên quan đến y tế mà thôi, "
            + "nếu không phải lĩnh vực y tế hãy trả lời rằng bạn không biết. "
            + "Câu trả lời của bạn phải có 2 phần: Phần dự đoán bệnh, phần lời khuyên. "
            + "Nếu câu hỏi có liên quan đến giờ khám bệnh hãy trả lời: "
            + "Hệ thống làm việc hàng tuần từ thứ 2 đến Chủ Nhật. Thời gian khám bệnh từ 7 giờ sáng đến 5 giờ 30 chiều."
            + "\nCâu hỏi: ";

    @GetMapping("/public/chatbot")
    public String chatBot(@RequestParam("query") String query) {
        query = MY_PROMPT + query;
        System.out.println(query);
        ChatGPTResponseDTO response = chatGPTConfig.getChatGPTRespone(query);

        return response.getChoices().get(0).getMessage().getContent();
    }

    @PostMapping("/auth/chatgptConsult/add-chatgptConsult/")
    @CrossOrigin
    public ResponseEntity<?> addChatgptConsult(
            @Valid @RequestBody AddChatgptConsultDTO addChatgptConsultDTO) {
        String message = "Có lỗi xảy ra!";
        User user = this.userService.findUserByUserIdAndActiveTrue(Integer.parseInt(addChatgptConsultDTO.getUserId()));

        if (user != null) {
            ChatgptConsult chatgptConsult = new ChatgptConsult();
            chatgptConsult.setUserId(user);

            String query = addChatgptConsultDTO.getPatientQuestion();
            query = MY_PROMPT + query;
            System.out.println(query);
            ChatGPTResponseDTO response = chatGPTConfig.getChatGPTRespone(query);

            chatgptConsult.setPatientQuestion(addChatgptConsultDTO.getPatientQuestion());
            chatgptConsult.setActive(Boolean.TRUE);
            chatgptConsult.setCreatedDate(new Date());

            System.out.println("+++++++++++++++++++++++++++++++++++++++");
            System.out.println(response.getChoices().get(0).getMessage().getContent());
            System.out.println("+++++++++++++++++++++++++++++++++++++++");
            chatgptConsult.setChatgptConsultAnswer(response.getChoices().get(0).getMessage().getContent());

            return new ResponseEntity<>(this.chatgptConsultService.addChatgptConsult(chatgptConsult),
                    HttpStatus.CREATED);
        } else {
            message = "User[" + addChatgptConsultDTO.getUserId() + "] không tồn tại!";
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/auth/user/{userId}/get-chatgptConsult/")
    @CrossOrigin
    public ResponseEntity<?> listSearchUser(@PathVariable(value = "userId") String userId,
            @RequestParam Map<String, String> params) {
        String message = "Có lỗi xảy ra!";
        User user = this.userService.findUserByUserIdAndActiveTrue(Integer.parseInt(userId));
        if (user != null) {
            String pageNumber = params.get("pageNumber");
            int defaultPageNumber = 0;
            Sort mySort = Sort.by("createdDate").descending();
            Pageable page = PageRequest.of(defaultPageNumber,
                    Integer.parseInt(this.environment.getProperty("spring.data.web.pageable.default-page-size")),
                    mySort);

            if (pageNumber != null && !pageNumber.isEmpty()) {
                page = PageRequest.of(Integer.parseInt(pageNumber),
                        Integer.parseInt(this.environment.getProperty("spring.data.web.pageable.default-page-size")),
                        mySort);
            }
            return new ResponseEntity<>(this.chatgptConsultService.findByUserIdOrderByCreatedDateDesc(user, page),
                    HttpStatus.OK);
        } else {
            message = "User[" + userId + "] không tồn tại!";
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }

}
