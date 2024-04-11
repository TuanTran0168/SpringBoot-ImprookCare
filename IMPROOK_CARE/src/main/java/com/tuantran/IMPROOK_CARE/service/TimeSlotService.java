/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.service;

import com.tuantran.IMPROOK_CARE.models.ProfileDoctor;
import com.tuantran.IMPROOK_CARE.models.Schedule;
import com.tuantran.IMPROOK_CARE.models.TimeSlot;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Administrator
 */
public interface TimeSlotService {
    List<TimeSlot> findTimeSlotByTimeDistanceIdAndActiveTrue(int timeDistanceId);

    List<?> getTimeSlotByTimeDistanceIdWithCheckRegister(int timeDistanceId, int profiledoctorId, String date);

    TimeSlot addTimeSlot(Date timeBegin, Date timeEnd, ProfileDoctor profileDoctor);

    TimeSlot updateTimeSlot(TimeSlot timeSlot);

    Optional<TimeSlot> findTimeSlotByTimeSlotIdAndActiveTrue(int timeDistanceId);

    Schedule addTimeSlotAndSchedule(Date timeBegin, Date timeEnd, ProfileDoctor profileDoctor);

    Schedule updateTimeSlotAndSchedule(TimeSlot timeSlot, String dateSchedule) throws NullPointerException;
}
