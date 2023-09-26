/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.service.Impl;

import com.tuantran.IMPROOK_CARE.models.TimeSlot;
import com.tuantran.IMPROOK_CARE.repository.TimeDistanceRepository;
import com.tuantran.IMPROOK_CARE.repository.TimeSlotRepository;
import com.tuantran.IMPROOK_CARE.service.TimeSlotService;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Administrator
 */
@Service
public class TimeSlotServiceImpl implements TimeSlotService {

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    @Autowired
    private TimeDistanceRepository timeDistanceRepository;

    @Override
    public List<TimeSlot> findTimeSlotByTimeDistanceIdAndActiveTrue(int timeDistanceId) {
        try {
            return this.timeSlotRepository.findTimeSlotByTimeDistanceIdAndActiveTrue(this.timeDistanceRepository.findTimeDistanceByTimeDistanceIdAndActiveTrue(timeDistanceId).get());
        } catch (NoSuchElementException ex) {
            return null;
        }
    }

}
