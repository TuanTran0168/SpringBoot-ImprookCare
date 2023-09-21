/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.repository;

import com.tuantran.IMPROOK_CARE.models.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Administrator
 */

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{
    Role findRoleByRoleNameAndActiveTrue(String roleName);
    Optional<Role> findRoleByRoleIdAndActiveTrue(int roleId);
}
