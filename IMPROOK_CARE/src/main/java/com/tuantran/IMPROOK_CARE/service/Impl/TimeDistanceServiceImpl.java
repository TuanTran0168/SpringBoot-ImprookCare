/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.service.Impl;

import com.tuantran.IMPROOK_CARE.models.TimeDistance;
import com.tuantran.IMPROOK_CARE.repository.TimeDistanceRepository;
import com.tuantran.IMPROOK_CARE.service.TimeDistanceService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Administrator
 */
@Service
public class TimeDistanceServiceImpl implements TimeDistanceService {

    @Autowired
    private TimeDistanceRepository timeDistanceRepository;

    @Override
    public List<TimeDistance> findTimeDistanceByActiveTrue() {
        return this.timeDistanceRepository.findTimeDistanceByActiveTrue();
    }

}
