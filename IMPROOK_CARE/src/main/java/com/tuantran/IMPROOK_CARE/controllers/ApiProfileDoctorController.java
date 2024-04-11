/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.controllers;

import com.tuantran.IMPROOK_CARE.dto.AddProfileDoctorDTO;
import com.tuantran.IMPROOK_CARE.dto.UpdateProfileDoctorDTO;
import com.tuantran.IMPROOK_CARE.models.ProfileDoctor;
import com.tuantran.IMPROOK_CARE.service.ProfileDoctorService;
import com.tuantran.IMPROOK_CARE.service.UserService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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
public class ApiProfileDoctorController {

    @Autowired
    private ProfileDoctorService profileDoctorService;

    @Autowired
    private UserService userService;

    @PostMapping("/auth/doctor/add-profile-doctor/")
    @CrossOrigin
    public ResponseEntity<String> addProfileDoctor(@Valid @RequestBody AddProfileDoctorDTO addProfileDoctorDTO) {
        String message = "Có lỗi xảy ra!";
        int check = this.profileDoctorService.addProfileDoctor(addProfileDoctorDTO);

        if (check == 1) {
            message = "Tạo hồ sơ bác sĩ thành công!";
            return new ResponseEntity<>(message, HttpStatus.OK);
        }

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/auth/doctor/update-profile-doctor/")
    @CrossOrigin
    public ResponseEntity<String> updateProfileDoctor(
            @Valid @RequestBody UpdateProfileDoctorDTO updateProfileDoctorDTO) {
        String message = "Có lỗi xảy ra!";
        int check = this.profileDoctorService.updateProfileDoctor(updateProfileDoctorDTO);

        if (check == 1) {
            message = "Cập nhật hồ sơ bác sĩ thành công!";
            return new ResponseEntity<>(message, HttpStatus.OK);
        }

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/public/profile-doctor/")
    @CrossOrigin
    public ResponseEntity<List<ProfileDoctor>> listProfileDoctor() {
        return new ResponseEntity<>(this.profileDoctorService.findAllProfileDoctorByActiveTrue(), HttpStatus.OK);
    }

    @GetMapping("/public/profile-doctor/{profileDoctorId}/")
    @CrossOrigin
    public ResponseEntity<ProfileDoctor> profileDoctorDetail(
            @PathVariable(value = "profileDoctorId") String profileDoctorId) {

        return new ResponseEntity<>(this.profileDoctorService
                .findProfileDoctorByProfileDoctorIdAndActiveTrue(Integer.parseInt(profileDoctorId)), HttpStatus.OK);
    }

    @GetMapping("/public/user/{userId}/profile-doctor/")
    @CrossOrigin
    public ResponseEntity<List<ProfileDoctor>> profileDoctorByUserId(@PathVariable(value = "userId") String userId) {

        return new ResponseEntity<>(
                this.profileDoctorService.findProfileDoctorByUserIdAndActiveTrue(Integer.parseInt(userId)),
                HttpStatus.OK);
    }

    @GetMapping("/public/search-profile-doctors/")
    @CrossOrigin
    public ResponseEntity<?> listSearchUser(@RequestParam Map<String, String> params) {
        return new ResponseEntity<>(this.profileDoctorService.findAllProfileDoctorPageSpec(params), HttpStatus.OK);
    }

    @DeleteMapping("/auth/doctor/soft-delete/profile-doctor/{profileDoctorId}/")
    @CrossOrigin
    public ResponseEntity<String> softDeleteProfileDoctor(
            @PathVariable(value = "profileDoctorId") String profileDoctorId) {
        String message = "Có lỗi xảy ra!";

        int check = this.profileDoctorService.softDeleteProfileDoctor(Integer.parseInt(profileDoctorId));

        if (check == 1) {
            message = "Xóa hồ sơ bác sĩ thành công!";
            return new ResponseEntity<>(message, HttpStatus.NO_CONTENT);
        } else if (check == 2) {
            message = "Xóa hồ sơ bác sĩ thất bại!";
        } else if (check == 3) {
            message = "Không tìm thấy hồ sơ bác sĩ để xóa";
        }

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    // @GetMapping("/auth/user/{userId}/profile-doctor-message/")
    //// @PreAuthorize("hasRole('USER')")
    // @CrossOrigin
    // public ResponseEntity<?>
    // getMessageProfileDoctorByUserIdPage(@PathVariable(value = "userId") String
    // userId, @RequestParam Map<String, String> params) {
    //
    // return new
    // ResponseEntity<>(this.profileDoctorService.getMessageProfileDoctorByUserIdPage(Integer.parseInt(userId),
    // params), HttpStatus.BAD_REQUEST);
    // }

    // Thử phân quyền xíu
    // Chỉ user nào login khớp với userId truyền vào mới được fetch
    // API Lấy toàn bộ ProfileDoctor nào có nhắn tin với UserId
    @GetMapping("/auth/user/{userId}/profile-doctor-message/")
    @CrossOrigin
    public ResponseEntity<?> getMessageProfileDoctorByUserIdPage(@PathVariable(value = "userId") String userId,
            @RequestParam Map<String, String> params) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            System.out.print(authentication.getPrincipal());

            if (authentication != null
                    && authentication.getPrincipal() instanceof org.springframework.security.core.userdetails.User) {
                org.springframework.security.core.userdetails.User userLogin = (org.springframework.security.core.userdetails.User) authentication
                        .getPrincipal();

                String usernameLogin = userLogin.getUsername();
                String usernameRequest = this.userService.findUserByUserIdAndActiveTrue(Integer.parseInt(userId))
                        .getUsername();

                if (!usernameLogin.equals(usernameRequest)) {
                    return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
                }
            } else {
                // Trường hợp này gần như không xảy ra vì không cung cấp token là thằng jwt đá
                // rồi (401)
                return new ResponseEntity<>("User is not logged in!", HttpStatus.UNAUTHORIZED);
            }
        } catch (NullPointerException e) {
            return new ResponseEntity<>("User with id[" + userId + "] not found!", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Something wrong here, Internal Server Error!",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(
                this.profileDoctorService.getMessageProfileDoctorByUserIdPage(Integer.parseInt(userId), params),
                HttpStatus.OK);
    }
}
