/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.controllers;

import com.tuantran.IMPROOK_CARE.dto.AddCommentDTO;
import com.tuantran.IMPROOK_CARE.dto.UpdateCommentDTO;
import com.tuantran.IMPROOK_CARE.service.CommentService;
import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Administrator
 */
@RestController
@RequestMapping("/api")
public class ApiCommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping(path = "/auth/add-comment/",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<String> addComment(@Valid AddCommentDTO addCommentDTO, @RequestPart("avatar") MultipartFile avatar) throws Exception {
        String message = "Có lỗi xảy ra!";
        int check = this.commentService.addComment(addCommentDTO, avatar);

        if (check == 1) {
            message = "Bình luận thành công!";
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else if (check == 2) {
            message = "Chưa khám không thể bình luận!";
        } else if (check == 0) {
            message = "Bình luận thất bại!";
        }

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @PostMapping(path = "/auth/update-comment/",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<String> updateComment(@Valid UpdateCommentDTO updateCommentDTO, @RequestPart("avatar") MultipartFile avatar) throws Exception {
        String message = "Có lỗi xảy ra!";
        int check = this.commentService.updateComment(updateCommentDTO, avatar);

        if (check == 1) {
            message = "Cập nhật bình luận thành công!";
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else if (check == 2) {
            message = "Không tìm thấy bình luận để cập nhật!";
        } else if (check == 3) {
            message = "Bình luận này bạn không được phép sửa!";
        } else if (check == 0) {
            message = "Cập nhật bình luận thất bại!";
        }

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/public/find-check-comment/")
    @CrossOrigin
    public ResponseEntity<?> findCheckComment(@RequestBody Map<String, String> params) {

        String userId = params.get("userId");
        String profileDoctorId = params.get("profileDoctorId");

        return new ResponseEntity<>(this.commentService.checkComment(Integer.parseInt(userId), Integer.parseInt(profileDoctorId)), HttpStatus.OK);
    }
}
