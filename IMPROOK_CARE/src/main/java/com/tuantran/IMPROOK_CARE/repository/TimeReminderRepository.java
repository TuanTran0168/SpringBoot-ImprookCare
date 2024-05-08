/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.repository;

import com.tuantran.IMPROOK_CARE.models.TimeReminder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

/**
 *
 * @author Administrator
 */
@Repository
@Transactional
public interface TimeReminderRepository extends JpaRepository<TimeReminder, Integer> {
    Optional<TimeReminder> findByTimeReminderId(Integer timeReminderId);
}
