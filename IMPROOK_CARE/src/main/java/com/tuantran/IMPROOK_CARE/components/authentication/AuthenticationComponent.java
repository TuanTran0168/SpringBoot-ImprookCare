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

/**
 *
 * @author Administrator
 */
@Component
public class AuthenticationComponent {

    @Autowired
    private AuthenticationManager authenticationManager;

    public void authenticateUser(String username, String password) throws Exception {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
//    public Authentication authenticateUser(String username, String password) throws Exception {
//        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        return authentication;
//    }
}
