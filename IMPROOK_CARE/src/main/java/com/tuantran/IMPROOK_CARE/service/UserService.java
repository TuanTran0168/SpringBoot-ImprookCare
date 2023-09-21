/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.service;

import com.tuantran.IMPROOK_CARE.dto.RegisterDTO;
import com.tuantran.IMPROOK_CARE.models.User;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Administrator
 */
public interface UserService extends UserDetailsService {
    User findUserByUsernameAndActiveTrue(String username);
    User findUserByUsername(String username);
    User addUser(Map<String, String> params, MultipartFile avatar);
    User registerUser(Map<String, String> params);
    User registerUser(RegisterDTO registerDTO);
    int updateUser(Map<String, String> params, MultipartFile avatar);
    List<User> findAllUser();
}
