/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.controllers;

import com.tuantran.IMPROOK_CARE.components.datetime.DateFormatComponent;
import com.tuantran.IMPROOK_CARE.dto.AddMedicalScheduleDTO;
import com.tuantran.IMPROOK_CARE.models.MedicalReminder;
import com.tuantran.IMPROOK_CARE.models.MedicalSchedule;
import com.tuantran.IMPROOK_CARE.service.MedicalReminderService;
import com.tuantran.IMPROOK_CARE.service.MedicalScheduleService;
import java.text.ParseException;
import java.util.Date;
import java.util.Optional;

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
public class ApiMedicalScheduleController {

    @Autowired
    private MedicalScheduleService medicalScheduleService;

    @Autowired
    private MedicalReminderService medicalReminderService;

    @Autowired
    private DateFormatComponent dateFormatComponent;

    @GetMapping("/auth/prescriptionId/{prescriptionId}/medical-schedule/")
    @CrossOrigin
    public ResponseEntity<?> listMedicalReminder(
            @PathVariable(value = "prescriptionId") int prescriptionId) {
        return ResponseEntity.ok()
                .body(this.medicalScheduleService.findMedicalScheduleByPrescriptionId(prescriptionId));
    }

    @PostMapping("/auth/add-medical-schedule/")
    @CrossOrigin
    public ResponseEntity<?> addMedicalSchedule(@RequestBody AddMedicalScheduleDTO addMedicalScheduleDTO) {
        try {
            String message = "Có lỗi xảy ra!";
            MedicalSchedule medicalSchedule = new MedicalSchedule();

            String medicalReminderId = addMedicalScheduleDTO.getMedicalReminderId();
            String customTime = addMedicalScheduleDTO.getCustomTime();
            String startDate = addMedicalScheduleDTO.getStartDate();

            /*
             * Nếu cái medicalReminderId KHÔNG null (tức là nó tạo tự động từ đơn thuốc)
             * thì cái customTime đó nó lấy từ TimeReminder luôn
             */
            if (medicalReminderId != null && !medicalReminderId.isEmpty()) {
                Optional<MedicalReminder> medicalReminderOptional = this.medicalReminderService
                        .findMedicalReminderByMedicalReminderId(Integer.parseInt(medicalReminderId));

                if (medicalReminderOptional.isPresent()) {
                    MedicalReminder medicalReminder = medicalReminderOptional.get();
                    medicalSchedule.setMedicalReminderId(medicalReminder);

                    medicalSchedule.setCustomTime(medicalReminder.getTimeReminderId().getTimeReminderValue());
                    // Nó còn phải set tên thuốc
                    // Nó còn phải set email của thằng cần nhắc nữa.
                } else {
                    message = "MedicalReminder[" + addMedicalScheduleDTO.getMedicalReminderId() + "] không tồn tại!";
                    return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
                }
            }

            /*
             * Nếu cái medicalReminderId nó null (tức là nó tạo bằng cơm)
             * thì cái customTime đó nó phải tự nhập
             * Nhưng tự nhập thì thiếu thông tin để gửi mail, cũng như tên thuốc (Đề xuất
             * thêm field ở database)
             */
            if (customTime != null && !customTime.isEmpty()) {
                Date customTimeParse = this.dateFormatComponent.myDateTimeFormat().parse(customTime);
                medicalSchedule.setCustomTime(customTimeParse);
            }

            if (startDate != null && !startDate.isEmpty()) {
                Date startDateParse = this.dateFormatComponent.myDateFormat().parse(startDate);
                medicalSchedule.setStartDate(startDateParse);
            }

            medicalSchedule.setActive(Boolean.TRUE);
            medicalSchedule.setCreatedDate(new Date());
            return new ResponseEntity<>(this.medicalScheduleService.addMedicalSchedule(medicalSchedule),
                    HttpStatus.CREATED);
        } catch (ParseException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    // @PostMapping("/auth/update-medical-schedule/")
    // @CrossOrigin
    // public ResponseEntity<?> updateMedicalSchedule() {

    // }
}
