/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.repository;

import com.tuantran.IMPROOK_CARE.models.User;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Administrator
 */

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Integer>{
    User findUserByUsernameAndActiveTrue(String username);
    
//    @Query("SELECT user FROM User user WHERE user.active = true")
    List<User> findUserByActiveTrue();
}
