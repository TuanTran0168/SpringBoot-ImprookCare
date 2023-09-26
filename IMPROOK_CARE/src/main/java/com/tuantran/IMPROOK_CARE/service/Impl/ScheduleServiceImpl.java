/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.service.Impl;

import com.tuantran.IMPROOK_CARE.components.datetime.DateFormatComponent;
import com.tuantran.IMPROOK_CARE.dto.AddScheduleDTO;
import com.tuantran.IMPROOK_CARE.models.Schedule;
import com.tuantran.IMPROOK_CARE.repository.ProfileDoctorRepository;
import com.tuantran.IMPROOK_CARE.repository.ScheduleRepository;
import com.tuantran.IMPROOK_CARE.repository.TimeSlotRepository;
import com.tuantran.IMPROOK_CARE.service.ScheduleService;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Administrator
 */
@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private ProfileDoctorRepository profileDoctorRepository;

    @Autowired
    private TimeSlotRepository timeSlotRepository;
    
    @Autowired
    DateFormatComponent dateFormatComponent;

    @Override
    public int addSchedule(AddScheduleDTO addScheduleDTO) {
        try {
            Schedule schedule = new Schedule();
            schedule.setProfileDoctorId(this.profileDoctorRepository.findProfileDoctorByProfileDoctorIdAndActiveTrue(Integer.parseInt(addScheduleDTO.getProfileDoctorId())).get());
            schedule.setDate(this.dateFormatComponent.myDateFormat().parse(addScheduleDTO.getDate()));
            schedule.setTimeSlotId(this.timeSlotRepository.findTimeSlotByTimeSlotIdAndActiveTrue(Integer.parseInt(addScheduleDTO.getTimeSlotId())).get());
            schedule.setCreatedDate(new Date());
            schedule.setActive(Boolean.TRUE);
            schedule.setBooked(Boolean.FALSE);
            
            this.scheduleRepository.save(schedule);
            return 1;
        } catch (ParseException ex) {
            Logger.getLogger(ScheduleServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public int addCustomSchedule(List<AddScheduleDTO> addScheduleDTOList) {
        
        for(AddScheduleDTO addScheduleDTO : addScheduleDTOList) {
            this.addSchedule(addScheduleDTO);
        }
        
        return 1;
    }

    @Override
    public Schedule findScheduleByIdAndActiveTrue(int scheduleId) {
        return this.scheduleRepository.findScheduleByScheduleIdAndActiveTrue(scheduleId).get();
    }

}
