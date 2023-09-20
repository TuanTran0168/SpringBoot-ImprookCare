/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.service.Impl;

import com.tuantran.IMPROOK_CARE.components.cloudinary.CloudinaryComponent;
import com.tuantran.IMPROOK_CARE.components.password.PasswordComponent;
import com.tuantran.IMPROOK_CARE.configs.cloudinary.CloudinaryConfig;
import com.tuantran.IMPROOK_CARE.models.Role;
import com.tuantran.IMPROOK_CARE.models.User;
import com.tuantran.IMPROOK_CARE.repository.RoleRepository;
import com.tuantran.IMPROOK_CARE.repository.UserRepository;
import com.tuantran.IMPROOK_CARE.service.UserService;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private PasswordComponent passworldService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CloudinaryComponent cloudinaryComponent;

    @Override
    public User findUserByUsername(String username) {
        return this.userRepository.findUserByUsernameAndActiveTrue(username);
    }

    @Override
    public User addUser(Map<String, String> params, MultipartFile avatar) {
        // Đăng ký 
        //B1: Tại trang xác thực sẽ dùng số điện thoại xác thực twilio trước, sau đó chuyển qua trang đăng ký
        //B2: Tại trang đăng ký sẽ có username là số điện thoại được xác thực ở trang xác thực lấy qua
        //B3: Ấn đăng ký
        //Tại trang xác thực cần:
        //phonenumber, confirm otp
        //Tại trang đăng ký cần
        // username(lấy phonenumber gán vào username - disable), password, comfirmPassword, phonenumber(lưu ngầm)
        //Admin cấp nik role bác sĩ sẽ tính sau

        User user = new User();
        String phonenumber = params.get("phonenumber");
        String username = phonenumber; //username sẽ là số điện thoại lấy qua
        String password = params.get("password");

        String firstname = params.get("firstname");
        String lastname = params.get("lastname");
        String gender = params.get("gender");

        // Đăng ký chỉ dùng 3 cái này thôi
        user.setUsername(username);
        user.setPassword(this.passworldService.PasswordEncoder().encode(password));
        user.setPhonenumber(phonenumber);
        
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
    public int updateUser(Map<String, String> params, MultipartFile avatar) {
        User user = new User();

        // Đăng ký chỉ dùng 2 cái này thôi
        String username = params.get("username");
        String password = params.get("password");

        String firstname = params.get("firstname");
        String lastname = params.get("lastname");
        String gender = params.get("gender");
        String phonenumber = params.get("phonenumber");

        // Đăng ký chỉ dùng 2 cái này thôi
        user.setUsername(username);
        user.setPassword(this.passworldService.PasswordEncoder().encode(password));

        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setGender(Boolean.parseBoolean(gender));
        user.setPhonenumber(phonenumber);

        if (avatar != null) {
            String linkCloudinaryAvatar = cloudinaryComponent.Cloudinary(avatar).get("secure_url").toString();
            user.setAvatar(linkCloudinaryAvatar);
        }

        this.userRepository.save(user);
        return 1;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsernameAndActiveTrue(username);

        if (user == null) {
            throw new UsernameNotFoundException("Username không tồn tại!");
        }

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
}
