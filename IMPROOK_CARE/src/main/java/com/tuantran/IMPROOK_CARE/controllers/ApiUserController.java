/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.controllers;

import com.tuantran.IMPROOK_CARE.components.authentication.AuthenticationComponent;
import com.tuantran.IMPROOK_CARE.components.twilio.SmsService;
import com.tuantran.IMPROOK_CARE.configs.jwt.JwtUtils;
import com.tuantran.IMPROOK_CARE.configs.twilio.TwilioConfiguration;
import com.tuantran.IMPROOK_CARE.dto.AddUserForAdminDTO;
import com.tuantran.IMPROOK_CARE.dto.ChangePasswordDTO;
import com.tuantran.IMPROOK_CARE.dto.ForgotPasswordDTO;
import com.tuantran.IMPROOK_CARE.dto.LoginDTO;
import com.tuantran.IMPROOK_CARE.dto.RegisterDTO;
import com.tuantran.IMPROOK_CARE.dto.UpdateUserForAdminDTO;
import com.tuantran.IMPROOK_CARE.dto.UpdateUserForUserDTO;
import com.tuantran.IMPROOK_CARE.models.Prescriptions;
import com.tuantran.IMPROOK_CARE.models.User;
import com.tuantran.IMPROOK_CARE.service.PrescriptionService;
import com.tuantran.IMPROOK_CARE.service.UserService;
import jakarta.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Administrator
 */
@RestController
@RequestMapping("/api")
public class ApiUserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationComponent authenticationComponent;
    
    @Autowired
    private PrescriptionService prescriptionService;

    @GetMapping("/public/test-xiu/")
    @CrossOrigin
    public ResponseEntity<?> test(@RequestParam int pageNumber) {
        return new ResponseEntity<>(this.userService.findAllUserPage(pageNumber).getContent(), HttpStatus.OK);
    }

    @GetMapping("/public/test-xiu-xiu/")
    @CrossOrigin
    public ResponseEntity<?> test(@RequestParam Map<String, String> params) {
        return new ResponseEntity<>(this.prescriptionService.getPrescriptionsByProfilePatientIdPageSpec(params), HttpStatus.OK);
    }

    @GetMapping("/public/users/")
    @CrossOrigin
    public ResponseEntity<List<User>> findUserByActiveTrue() {
        return ResponseEntity.ok().body(this.userService.findAllUser());
    }

    @PostMapping("/public/login/")
    @CrossOrigin
    public ResponseEntity<String> login(@Valid @RequestBody LoginDTO loginDTO) throws Exception {

        this.authenticationComponent.authenticateUser(loginDTO.getUsername(), loginDTO.getPassword());
        final UserDetails userDetails = userService.loadUserByUsername(loginDTO.getUsername());

        String jwtResponse = jwtUtils.generateJwtToken(userDetails);
        return ResponseEntity.ok().body(jwtResponse);
    }

    @PostMapping("/public/register/")
    @CrossOrigin
    public ResponseEntity<String> register(@Valid @RequestBody RegisterDTO registerDTO) throws Exception {
        String message = "Có lỗi xảy ra!";
        int check = this.userService.registerUser(registerDTO);

        if (check == 1) {
            message = "Đăng ký thành công!";
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else if (check == 0) {
            message = "Đăng ký thất bại!";
        }

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/auth/current-user/")
    @CrossOrigin
    public ResponseEntity<User> details(Principal user) {
        if (user != null) {
            User u = this.userService.findUserByUsername(user.getName());
            return new ResponseEntity<>(u, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping(path = "/auth/update-user/",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<String> updateUserForUser(@Valid UpdateUserForUserDTO updateUserForUserDTO, @RequestPart("avatar") MultipartFile avatar) {

        // Dùng RequestPart để gửi ảnh thì dẹp luôn @RequestBody của thằng user trong DTO chứ không nó lỗi 
        // org.springframework.web.HttpMediaTypeNotSupportedException: Content-Type 'multipart/form-data;boundary=--------------------------993163030984227239709456;charset=UTF-8' is not supported
        // Chắc là 2 loại Request khác nhau nó không chịu
        // fetch bên postman thì dùng Body form-data gửi đủ hết thông tin trong DTO + 1 avatar (file)
        String message = "Có lỗi xảy ra!";
        int check = this.userService.updateUser(updateUserForUserDTO, avatar);

        if (check == 1) {
            message = "Cập nhật thông tin thành công!";
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else if (check == 2) {
            message = "Người dùng không tồn tại!";
        }

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @PostMapping(path = "/auth/admin/add-user/",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<String> addUser(@Valid AddUserForAdminDTO addUserForAdminDTO, @RequestPart("avatar") MultipartFile avatar) throws Exception {
        String message = "Có lỗi xảy ra!";
        int check = this.userService.addUser(addUserForAdminDTO, avatar);

        if (check == 1) {
            message = "Đăng ký thành công!";
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else if (check == 2) {
            message = "Số điện thoại đã tồn tại!";
        } else if (check == 0) {
            message = "Đăng ký thất bại!";
        }

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @PostMapping(path = "/auth/admin/update-user/",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<String> updateUserForAdmin(@Valid UpdateUserForAdminDTO updateUserForAdminDTO, @RequestPart("avatar") MultipartFile avatar) {

        // Dùng RequestPart để gửi ảnh thì dẹp luôn @RequestBody của thằng user trong DTO chứ không nó lỗi 
        // org.springframework.web.HttpMediaTypeNotSupportedException: Content-Type 'multipart/form-data;boundary=--------------------------993163030984227239709456;charset=UTF-8' is not supported
        // Chắc là 2 loại Request khác nhau nó không chịu
        // fetch bên postman thì dùng Body form-data gửi đủ hết thông tin trong DTO + 1 avatar (file)
        String message = "Có lỗi xảy ra!";
        int check = this.userService.updateUser(updateUserForAdminDTO, avatar);

        if (check == 1) {
            message = "Cập nhật thông tin thành công!";
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else if (check == 2) {
            message = "Người dùng không tồn tại!";
        } else if (check == 0) {
            message = "Cập nhật thông tin thất bại!";
        }

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/auth/change-password/")
    @CrossOrigin
    public ResponseEntity<String> changePassword(@Valid @RequestBody ChangePasswordDTO changePasswordDTO) throws Exception {
        String message = "Có lỗi xảy ra!";
        int check = this.userService.changePassword(changePasswordDTO);

        if (check == 1) {
            message = "Đổi mật khẩu thành công!";
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else if (check == 2) {
            message = "Người dùng không tồn tại!";
        } else if (check == 3) {
            message = "Mật khẩu hiện tại và mật khẩu cũ không khớp!";
        } else if (check == 0) {
            message = "Đổi mật khẩu thất bại!";
        }

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/public/forgot-password/")
    @CrossOrigin
    public ResponseEntity<String> forgotPassword(@Valid @RequestBody ForgotPasswordDTO forgotPasswordDTO) throws Exception {
        String message = "Có lỗi xảy ra!";
        int check = this.userService.forgotPassword(forgotPasswordDTO);

        if (check == 1) {
            message = "Đổi mật khẩu thành công!";
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else if (check == 2) {
            message = "Người dùng không tồn tại!";
        } else if (check == 0) {
            message = "Đổi mật khẩu thất bại!";
        }

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/public/user/{userId}/")
    @CrossOrigin
    public ResponseEntity<User> getUserById(@PathVariable(value = "userId") String userId) throws Exception {

        return new ResponseEntity<>(this.userService.findUserByUserIdAndActiveTrue(Integer.parseInt(userId)), HttpStatus.OK);
    }

    @GetMapping("/public/search-users/")
    @CrossOrigin
    public ResponseEntity<?> listSearchUser(@RequestParam Map<String, String> params) {
        return new ResponseEntity<>(this.userService.findAllUserPageSpec(params), HttpStatus.OK);
    }
    
    @DeleteMapping("/auth/admin/soft-delete/user/{userId}/")
    @CrossOrigin
    public ResponseEntity<String> softDeleteUser(@PathVariable(value = "userId") String userId) {
        String message = "Có lỗi xảy ra!";

        int check = this.userService.softDeleteUser(Integer.parseInt(userId));

        if (check == 1) {
            message = "Xóa người dùng thành công!";
            return new ResponseEntity<>(message, HttpStatus.NO_CONTENT);
        } else if (check == 2) {
            message = "Xóa người dùng thất bại!";
        }
        else if (check == 3) {
            message = "Không tìm thấy người dùng để xóa";
        }

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}
