/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.service.Impl;

import com.tuantran.IMPROOK_CARE.models.Role;
import com.tuantran.IMPROOK_CARE.repository.RoleRepository;
import com.tuantran.IMPROOK_CARE.service.RoleService;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Administrator
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role findRoleByRoleNameAndActiveTrue(String roleName) {
        try {
            Optional<Role> roleOptional = this.roleRepository.findRoleByRoleNameAndActiveTrue(roleName);
            if (roleOptional.isPresent()) {
                return roleOptional.get();
            } else {
                return null;
            }
        } catch (NoSuchElementException ex) {
            return null;
        }
    }

    @Override
    public Role findRoleByRoleIdAndActiveTrue(int roleId) {
        try {
            Optional<Role> roleOptional = this.roleRepository.findRoleByRoleIdAndActiveTrue(roleId);
            if (roleOptional.isPresent()) {
                return roleOptional.get();
            } else {
                return null;
            }
        } catch (NoSuchElementException ex) {
            return null;
        }
    }

    @Override
    public List<Role> findRoleByActiveTrue() {
        return this.roleRepository.findRoleByActiveTrue();
    }

}
