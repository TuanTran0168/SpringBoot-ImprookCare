/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.service.Impl;

import com.tuantran.IMPROOK_CARE.components.cloudinary.CloudinaryComponent;
import com.tuantran.IMPROOK_CARE.components.password.PasswordComponent;
import com.tuantran.IMPROOK_CARE.configs.cloudinary.CloudinaryConfig;
import com.tuantran.IMPROOK_CARE.dto.RegisterDTO;
import com.tuantran.IMPROOK_CARE.dto.UpdateUserForUserDTO;
import com.tuantran.IMPROOK_CARE.models.Role;
import com.tuantran.IMPROOK_CARE.models.User;
import com.tuantran.IMPROOK_CARE.repository.RoleRepository;
import com.tuantran.IMPROOK_CARE.repository.UserRepository;
import com.tuantran.IMPROOK_CARE.service.UserService;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Administrator
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordComponent passworldComponent;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CloudinaryComponent cloudinaryComponent;

    @Override
    public User findUserByUsernameAndActiveTrue(String username) {
        return this.userRepository.findUserByUsernameAndActiveTrue(username).get();
    }

    @Override
    public User addUser(Map<String, String> params, MultipartFile avatar) {
        User user = new User();
        String phonenumber = params.get("phonenumber");
        String username = phonenumber; //username sẽ là số điện thoại lấy qua
        String password = params.get("password");

        String firstname = params.get("firstname");
        String lastname = params.get("lastname");
        String gender = params.get("gender");

        // Đăng ký chỉ dùng 3 cái này thôi
        user.setUsername(username);
        user.setPassword(this.passworldComponent.PasswordEncoder().encode(password));
//        user.setPhonenumber(phonenumber);

        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setGender(Boolean.parseBoolean(gender));

        if (avatar != null) {
            String linkCloudinaryAvatar = cloudinaryComponent.Cloudinary(avatar).get("secure_url").toString();
            user.setAvatar(linkCloudinaryAvatar);
        }

        return this.userRepository.save(user);
    }

    @Override
    public User registerUser(RegisterDTO registerDTO) {
        // Thực ra ở tầng verification đã xác thực phonenumber qua twilio rồi nên kiểm tra thêm cho yên tâm
        Optional<User> userOptional = this.userRepository.findUserByUsername(registerDTO.getUsername());

        if (!userOptional.isPresent()) {
            User userRegister = new User();
            userRegister.setUsername(registerDTO.getUsername()); //Username là phonenumber (Quy ước mới)
            userRegister.setPassword(this.passworldComponent.PasswordEncoder().encode(registerDTO.getPassword()));
            userRegister.setFirstname(registerDTO.getFirstname());
            userRegister.setLastname(registerDTO.getLastname());
            userRegister.setGender(registerDTO.getGender());
            userRegister.setRoleId(this.roleRepository.findRoleByRoleNameAndActiveTrue("USER").get());
            userRegister.setCreatedDate(new Date());
            userRegister.setActive(Boolean.TRUE);
            return this.userRepository.save(userRegister);

        }

        return null; // Tồn tại phonenumber
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findUserByUsernameAndActiveTrue(username);

        if (!userOptional.isPresent()) {
            throw new UsernameNotFoundException("Username không tồn tại!");
        }

        User user = userOptional.get();
        Set<GrantedAuthority> authorities = new HashSet<>();
        Optional<Role> role = this.roleRepository.findById(user.getRoleId().getRoleId());
        String roleName = role.get().getRoleName();
        authorities.add(new SimpleGrantedAuthority(roleName));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), authorities);
    }

    @Override
    public List<User> findAllUser() {
        return userRepository.findUserByActiveTrue();
    }

    @Override
    public User findUserByUsername(String username) {
        return this.userRepository.findUserByUsername(username).get();
    }

    @Override
    public int updateUser(UpdateUserForUserDTO updateUserForUserDTO, MultipartFile avatar) {
        try {
            Optional<User> userOptional = this.userRepository.findUserByUserIdAndActiveTrue(Integer.parseInt(updateUserForUserDTO.getUserId()));
            if (userOptional.isPresent()) {
                
                User user = userOptional.get();
                user.setUserId(Integer.parseInt(updateUserForUserDTO.getUserId()));
                user.setFirstname(updateUserForUserDTO.getFirstname());
                user.setLastname(updateUserForUserDTO.getLastname());
                user.setGender(updateUserForUserDTO.getGender());

                if (avatar != null) {
                    try {
                        String linkCloudinaryAvatar = cloudinaryComponent.Cloudinary(avatar).get("secure_url").toString();
                        user.setAvatar(linkCloudinaryAvatar);
                    } catch (Exception ex) {
                        Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                this.userRepository.save(user);
                return 1;
            } else {
                return 2; // Có tìm được ai đâu mà update
            }

        } catch (DataAccessException ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    @Override
    public User findUserByUserIdAndActiveTrue(int userId) {
        return this.userRepository.findUserByUserIdAndActiveTrue(userId).get();
    }
}
