/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.controllers;

import com.tuantran.IMPROOK_CARE.dto.AddScheduleDTO;
import com.tuantran.IMPROOK_CARE.models.Schedule;
import com.tuantran.IMPROOK_CARE.models.TimeDistance;
import com.tuantran.IMPROOK_CARE.models.TimeSlot;
import com.tuantran.IMPROOK_CARE.service.ScheduleService;
import com.tuantran.IMPROOK_CARE.service.TimeDistanceService;
import com.tuantran.IMPROOK_CARE.service.TimeSlotService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Administrator
 */
@RestController
@RequestMapping("/api")
public class ApiScheduleController {

    @Autowired
    private TimeDistanceService timeDistanceService;

    @Autowired
    private TimeSlotService timeSlotService;

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping("/public/timeDistance/")
    @CrossOrigin
    public ResponseEntity<List<TimeDistance>> listTimeDistance() {
        return ResponseEntity.ok().body(this.timeDistanceService.findTimeDistanceByActiveTrue());
    }

    @GetMapping("/public/timeDistance/{timeDistanceId}/timeSlot/")
    @CrossOrigin
    public ResponseEntity<List<TimeSlot>> listTimeSlot(@PathVariable(value = "timeDistanceId") String timeDistanceId) {
        return ResponseEntity.ok()
                .body(this.timeSlotService.findTimeSlotByTimeDistanceIdAndActiveTrue(Integer.parseInt(timeDistanceId)));
    }

    @PostMapping("/auth/doctor/add-schedule/")
    @CrossOrigin
    public ResponseEntity<String> addSchedule(@Valid @RequestBody AddScheduleDTO addScheduleDTO) {
        String message = "Có lỗi xảy ra!";
        int check = this.scheduleService.addSchedule(addScheduleDTO);
        if (check == 1) {
            message = "Đăng ký thành công lịch chữa bệnh!";
            return new ResponseEntity<>(message, HttpStatus.OK);
        }

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/public/check-scheduled/")
    @CrossOrigin
    public ResponseEntity<String> checkScheduled(@RequestBody Map<String, String> params) {
        String profileDoctorId = params.get("profileDoctorId");
        String date = params.get("date");
        String timeSlotId = params.get("timeSlotId");
        String message = "Có lỗi xảy ra!";
        int check = this.scheduleService.isScheduleExists(Integer.parseInt(profileDoctorId), date,
                Integer.parseInt(timeSlotId));
        if (check == 1) {
            message = "Lịch chữa bệnh đã đăng ký!";
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else if (check == 0) {
            message = "Lịch chữa bệnh chưa đăng ký!";
            return new ResponseEntity<>(message, HttpStatus.OK);
        }

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);

    }

    @PostMapping("/public/find-check-scheduled/")
    @CrossOrigin
    public ResponseEntity<Schedule> findScheduleByProfileDoctorIdAndDateAndTimeSlotIdAndActiveTrue(
            @RequestBody Map<String, String> params) {
        String profileDoctorId = params.get("profileDoctorId");
        String date = params.get("date");
        String timeSlotId = params.get("timeSlotId");
        Schedule schedule = this.scheduleService.findScheduleByProfileDoctorIdAndDateAndTimeSlotIdAndActiveTrue(
                Integer.parseInt(profileDoctorId), date, Integer.parseInt(timeSlotId));

        return new ResponseEntity<>(schedule, HttpStatus.OK);
    }

    /*
     * Tìm ra danh sách các Time Slot bằng Time Distance
     * Và kiểm tra xem Time Slot đó đã được bác sĩ đăng ký hay chưa.
     * Nếu các tham số kia là null thì nó sẽ lấy Time Slot như api lấy Time Slot
     * theo timeDistance bình thường
     */
    @GetMapping("/auth/doctor/timeDistance/{timeDistanceId}/timeSlot-check-register/")
    @CrossOrigin
    public ResponseEntity<?> getTimeSlotByTimeDistanceIdWithCheckRegister(
            @PathVariable(value = "timeDistanceId") String timeDistanceId,
            @RequestParam Map<String, String> params) {
        String profileDoctorId = params.get("profileDoctorId");
        String date = params.get("date");

        if ((timeDistanceId != null && !timeDistanceId.isEmpty())
                && (profileDoctorId != null && !profileDoctorId.isEmpty()) && (date != null && !date.isEmpty())) {
            List<?> listTimeSlotWithCheckRegister = this.timeSlotService.getTimeSlotByTimeDistanceIdWithCheckRegister(
                    Integer.parseInt(timeDistanceId),
                    Integer.parseInt(profileDoctorId), date);

            return new ResponseEntity<>(listTimeSlotWithCheckRegister, HttpStatus.OK);
        }

        return new ResponseEntity<>(
                this.timeSlotService.findTimeSlotByTimeDistanceIdAndActiveTrue(Integer.parseInt(timeDistanceId)),
                HttpStatus.OK);
    }
}
