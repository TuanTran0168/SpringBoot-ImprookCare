/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.service.Impl;

import com.tuantran.IMPROOK_CARE.models.Role;
import com.tuantran.IMPROOK_CARE.repository.RoleRepository;
import com.tuantran.IMPROOK_CARE.service.RoleService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Administrator
 */
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleRepository roleRepository;
    
    @Override
    public Role findRoleByRoleNameAndActiveTrue(String roleName) {
        return this.roleRepository.findRoleByRoleNameAndActiveTrue(roleName);
    }

    @Override
    public Optional<Role> getRoleByRoleId(int roleId) {
        return this.roleRepository.findById(roleId);
    }
    
}
