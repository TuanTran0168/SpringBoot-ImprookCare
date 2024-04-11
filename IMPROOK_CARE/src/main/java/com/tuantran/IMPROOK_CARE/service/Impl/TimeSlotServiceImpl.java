/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.service.Impl;

// import com.tuantran.IMPROOK_CARE.components.datetime.DateFormatComponent;
import com.tuantran.IMPROOK_CARE.dto.TimeSlotWithCheckRegisterDTO;
import com.tuantran.IMPROOK_CARE.models.ProfileDoctor;
import com.tuantran.IMPROOK_CARE.models.Schedule;
import com.tuantran.IMPROOK_CARE.models.TimeSlot;
import com.tuantran.IMPROOK_CARE.repository.TimeDistanceRepository;
import com.tuantran.IMPROOK_CARE.repository.TimeSlotRepository;
import com.tuantran.IMPROOK_CARE.service.ScheduleService;
import com.tuantran.IMPROOK_CARE.service.TimeSlotService;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

// import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
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
public class TimeSlotServiceImpl implements TimeSlotService {

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    @Autowired
    private TimeDistanceRepository timeDistanceRepository;

    @Autowired
    private ScheduleService scheduleService;

    // @Autowired
    // private DateFormatComponent dateFormatComponent;

    @Override
    public List<TimeSlot> findTimeSlotByTimeDistanceIdAndActiveTrue(int timeDistanceId) {
        try {
            return this.timeSlotRepository.findTimeSlotByTimeDistanceIdAndActiveTrue(
                    this.timeDistanceRepository.findTimeDistanceByTimeDistanceIdAndActiveTrue(timeDistanceId).get());
        } catch (NoSuchElementException ex) {
            return null;
        }
    }

    /*
     * Tìm ra danh sách các Time Slot bằng Time Distance
     * Và kiểm tra xem Time Slot đó đã được bác sĩ đăng ký hay chưa.
     */
    @Override
    public List<?> getTimeSlotByTimeDistanceIdWithCheckRegister(int timeDistanceId, int profiledoctorId,
            String date) {
        List<TimeSlot> timeSlotList = this.findTimeSlotByTimeDistanceIdAndActiveTrue(timeDistanceId);

        List<TimeSlotWithCheckRegisterDTO> timeSlotCheckedList = new ArrayList<>();
        Boolean check = false;

        for (TimeSlot timeSlot : timeSlotList) {
            if (this.scheduleService.findScheduleByProfileDoctorIdAndDateAndTimeSlotIdAndActiveTrue(profiledoctorId,
                    date, timeSlot.getTimeSlotId()) == null) {
                check = false;
            } else {
                check = true;
            }

            TimeSlotWithCheckRegisterDTO timeSlotChecked = new TimeSlotWithCheckRegisterDTO(timeSlot, check);
            timeSlotCheckedList.add(timeSlotChecked);
        }

        return timeSlotCheckedList;
    }

    @Override
    public TimeSlot addTimeSlot(Date timeBegin, Date timeEnd, ProfileDoctor profileDoctor) {
        TimeSlot timeSlot = new TimeSlot();

        timeSlot.setTimeBegin(timeBegin);
        timeSlot.setTimeEnd(timeEnd);
        timeSlot.setProfileDoctorId(profileDoctor);
        timeSlot.setActive(Boolean.TRUE);
        timeSlot.setCreatedDate(new Date());

        return this.timeSlotRepository.save(timeSlot);
    }

    @Override
    public TimeSlot updateTimeSlot(TimeSlot timeSlot) {
        return this.timeSlotRepository.save(timeSlot);
    }

    @Override
    public Optional<TimeSlot> findTimeSlotByTimeSlotIdAndActiveTrue(int timeDistanceId) {
        return this.timeSlotRepository.findTimeSlotByTimeSlotIdAndActiveTrue(timeDistanceId);
    }

    /*
     * Tạo và lưu TimeSlot xong sau đó tạo luôn Schedule tương ứng
     * Lấy time begin làm gốc cho date của schedule
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Schedule addTimeSlotAndSchedule(Date timeBegin, Date timeEnd, ProfileDoctor profileDoctor) {
        TimeSlot timeSlot = this.addTimeSlot(timeBegin, timeEnd, profileDoctor);
        Schedule schedule = this.scheduleService.addSchedule(timeSlot, timeBegin, profileDoctor);

        return schedule;
    }

    /*
     * Update TimeSlot xong sau đó Update luôn date trong Schedule tương ứng
     * Lấy time begin làm gốc cho date của schedule
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Schedule updateTimeSlotAndSchedule(TimeSlot timeSlot, String dateSchedule) throws NullPointerException {
        this.timeSlotRepository.save(timeSlot);

        int timeSlotId = timeSlot.getTimeSlotId();
        ProfileDoctor profileDoctor = timeSlot.getProfileDoctorId();
        Date timeBeginNew = timeSlot.getTimeBegin();

        Schedule schedule = this.scheduleService.findScheduleByProfileDoctorIdAndDateAndTimeSlotIdAndActiveTrue(
                profileDoctor.getProfileDoctorId(), dateSchedule, timeSlotId);

        schedule.setDate(timeBeginNew);

        return this.scheduleService.updateSchedule(schedule);
    }

}
