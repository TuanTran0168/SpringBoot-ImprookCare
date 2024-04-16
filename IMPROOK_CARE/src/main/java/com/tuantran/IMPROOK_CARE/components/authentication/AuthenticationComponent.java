/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.components.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.tuantran.IMPROOK_CARE.service.UserService;

/**
 *
 * @author Administrator
 */
@Component
public class AuthenticationComponent {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    public void authenticateUser(String username, String password) throws Exception {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    // public Authentication authenticateUser(String username, String password)
    // throws Exception {
    // Authentication authentication = authenticationManager.authenticate(new
    // UsernamePasswordAuthenticationToken(username, password));
    // SecurityContextHolder.getContext().setAuthentication(authentication);
    // return authentication;
    // }

    public boolean isUserAuthorized(Authentication authentication, String userId) {
        if (authentication != null
                && authentication.getPrincipal() instanceof org.springframework.security.core.userdetails.User) {
            org.springframework.security.core.userdetails.User userLogin = (org.springframework.security.core.userdetails.User) authentication
                    .getPrincipal();
            String usernameLogin = userLogin.getUsername();
            String usernameRequest = this.userService.findUserByUserIdAndActiveTrue(Integer.parseInt(userId))
                    .getUsername();
            return usernameLogin.equals(usernameRequest);
        }

        /*
         * User chưa login
         * Trường hợp này gần như không xảy ra vì không cung cấp token là thằng jwt đá
         * ra rồi (Ví dụ)
         * {
         * "path": "/api/auth/profileDoctor/14/get-message-detail/20/",
         * "message": "Full authentication is required to access this resource",
         * "status": 401
         * }
         */
        return false;
    }
}
