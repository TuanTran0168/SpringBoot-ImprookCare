/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.service;

import com.tuantran.IMPROOK_CARE.dto.AddScheduleDTO;
import com.tuantran.IMPROOK_CARE.models.ProfileDoctor;
import com.tuantran.IMPROOK_CARE.models.Schedule;
import com.tuantran.IMPROOK_CARE.models.TimeSlot;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface ScheduleService {

    int addSchedule(AddScheduleDTO addScheduleDTO);

    int addCustomSchedule(List<AddScheduleDTO> addScheduleDTOList);

    Schedule findScheduleByIdAndActiveTrue(int scheduleId);

    Schedule findScheduleByProfileDoctorIdAndDateAndTimeSlotIdAndActiveTrue(int profiledoctorId, String date, int timeSlotId);

    int isScheduleExists(int profiledoctorId, String date, int timeSlotId);

    int softDeleteSchedule(int scheduleId);

    List<Schedule> findScheduleByProfileDoctorIdAndActiveTrue(int profiledoctorId);
}
