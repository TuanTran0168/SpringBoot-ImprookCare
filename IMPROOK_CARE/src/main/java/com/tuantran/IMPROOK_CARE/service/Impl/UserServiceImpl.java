/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.service.Impl;

import com.tuantran.IMPROOK_CARE.components.PasswordService;
import com.tuantran.IMPROOK_CARE.models.Role;
import com.tuantran.IMPROOK_CARE.models.User;
import com.tuantran.IMPROOK_CARE.repository.RoleRepository;
import com.tuantran.IMPROOK_CARE.repository.UserRepository;
import com.tuantran.IMPROOK_CARE.service.UserService;
import java.util.HashSet;
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
    private PasswordService passworldService;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public User findUserByUsername(String username) {
        return this.userRepository.findUserByUsernameAndActiveTrue(username);
    }

    @Override
    public User addUser(Map<String, String> params, MultipartFile avatar) {
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
        
        return this.userRepository.save(user);
    }

    @Override
    public int updateUser(Map<String, String> params, MultipartFile avatar) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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

}
