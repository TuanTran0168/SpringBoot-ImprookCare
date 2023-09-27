/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.controllers;

import com.tuantran.IMPROOK_CARE.dto.AddScheduleDTO;
import com.tuantran.IMPROOK_CARE.models.TimeDistance;
import com.tuantran.IMPROOK_CARE.models.TimeSlot;
import com.tuantran.IMPROOK_CARE.service.ScheduleService;
import com.tuantran.IMPROOK_CARE.service.TimeDistanceService;
import com.tuantran.IMPROOK_CARE.service.TimeSlotService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
        return ResponseEntity.ok().body(this.timeSlotService.findTimeSlotByTimeDistanceIdAndActiveTrue(Integer.parseInt(timeDistanceId)));
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
}
