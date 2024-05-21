/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.controllers;

import com.tuantran.IMPROOK_CARE.components.datetime.DateFormatComponent;
import com.tuantran.IMPROOK_CARE.dto.AddMedicalScheduleDTO;
import com.tuantran.IMPROOK_CARE.dto.UpdateMedicalScheduleDTO;
import com.tuantran.IMPROOK_CARE.models.MedicalReminder;
import com.tuantran.IMPROOK_CARE.models.MedicalSchedule;
import com.tuantran.IMPROOK_CARE.models.User;
import com.tuantran.IMPROOK_CARE.service.MedicalReminderService;
import com.tuantran.IMPROOK_CARE.service.MedicalScheduleService;
import com.tuantran.IMPROOK_CARE.service.UserService;

import jakarta.validation.Valid;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Autowired
    private UserService userService;

    @GetMapping("/auth/prescriptionId/{prescriptionId}/medical-schedule/")
    @CrossOrigin
    public ResponseEntity<?> listMedicalReminder(
            @PathVariable(value = "prescriptionId") int prescriptionId) {
        return ResponseEntity.ok()
                .body(this.medicalScheduleService.findMedicalScheduleByPrescriptionId(prescriptionId));
    }

    @PostMapping("/auth/add-medical-schedule/")
    @CrossOrigin
    public ResponseEntity<?> addMedicalSchedule(@Valid @RequestBody AddMedicalScheduleDTO addMedicalScheduleDTO) {
        try {
            String message = "Có lỗi xảy ra!";
            MedicalSchedule medicalSchedule = new MedicalSchedule();

            String medicalReminderId = addMedicalScheduleDTO.getMedicalReminderId();
            String customTime = addMedicalScheduleDTO.getCustomTime();
            String startDate = addMedicalScheduleDTO.getStartDate();
            String medicineName = addMedicalScheduleDTO.getMedicineName();
            String email = addMedicalScheduleDTO.getEmail();
            String userId = addMedicalScheduleDTO.getUserId();

            /*
             * Nếu cái medicalReminderId KHÔNG null (tức là nó tạo tự động từ đơn thuốc)
             * thì cái customTime đó nó lấy từ TimeReminder luôn
             * 
             * ========= HOW TO USE =========
             * {
             * "medicalReminderId": "1",
             * "customTime": "2024-08-28 13:26:30",
             * "startDate": "2024-08-28",
             * "medicineName": "Thuốc an thần",
             * "email": "2051050549tuan@ou.edu.vn"
             * "userId": "1"
             * }
             * 
             * TH1: nó tạo tự động từ đơn thuốc
             * - Bắt buộc có tham số medicalReminderId, startDate
             * - Thì JSON lúc gửi không cần gửi customTime, medicineName, email (vì nó lấy
             * tự động)
             * 
             * 
             * TH2: Nó tạo tự động nhưng muốn sửa thông tin khác với timeCustom tự động từ
             * timeReminder
             * - Gửi JSON giống như TH1 (nhưng có thể gửi thêm các thông số mà nó muốn sửa
             * như: medicineName, email, customTime)
             *
             * 
             * TH3: Nó tự tạo riêng
             * - Tham số bắt buộc: startDate
             * - Vì tạo riêng nên không có medicalReminderId
             * - Thì JSON lúc gửi bắt buộc phải có cả customTime, medicineName, email.
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
                    medicalSchedule.setMedicineName(medicalReminder.getPrescriptionDetailId().getMedicineName());
                    medicalSchedule.setEmail(medicalReminder.getPrescriptionDetailId().getPrescriptionId()
                            .getBookingId().getProfilePatientId().getEmail());
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
             * 
             * Đã thêm 2 field ở database version 45
             */
            if (customTime != null && !customTime.isEmpty()) {
                Date customTimeParse = this.dateFormatComponent.myDateTimeFormat().parse(customTime);
                medicalSchedule.setCustomTime(customTimeParse);
            }

            if (startDate != null && !startDate.isEmpty()) {
                Date startDateParse = this.dateFormatComponent.myDateFormat().parse(startDate);
                medicalSchedule.setStartDate(startDateParse);
            }

            if (email != null && !email.isEmpty()) {
                medicalSchedule.setEmail(email);
            }

            if (medicineName != null && !medicineName.isEmpty()) {
                medicalSchedule.setMedicineName(medicineName);
            }

            User user = this.userService.findUserByUserIdAndActiveTrue(Integer.parseInt(userId));

            if (user != null) {
                medicalSchedule.setUserId(user);
            } else {
                message = "User[" + addMedicalScheduleDTO.getUserId() + "] không tồn tại!";
                return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
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

    @PostMapping("/auth/add-list-medical-schedule/")
    @CrossOrigin
    public ResponseEntity<?> addListMedicalSchedule(
            @Valid @RequestBody List<AddMedicalScheduleDTO> addListMedicalScheduleDTO) {
        try {
            int countSuccess = 0;
            int countFailure = 0;
            for (AddMedicalScheduleDTO addMedicalScheduleDTO : addListMedicalScheduleDTO) {
                ResponseEntity<?> responseEntity = this.addMedicalSchedule(addMedicalScheduleDTO);

                if (responseEntity.getStatusCode() == HttpStatus.CREATED) {
                    MedicalSchedule medicalSchedule = (MedicalSchedule) responseEntity.getBody();

                    if (medicalSchedule != null) {
                        countSuccess++;
                    } else {
                        countFailure++;
                    }
                    System.out.println("\nMedicalSchedule[" + addMedicalScheduleDTO.getMedicineName() + "] - "
                            + responseEntity.getStatusCode() + "\n");
                } else {
                    countFailure++;
                    System.out.println("\nMedicalSchedule[" + addMedicalScheduleDTO.getMedicineName() + "] - "
                            + responseEntity.getStatusCode() + "\n");
                }
            }

            Map<String, Integer> result = new HashMap<>();
            result.put("Success", countSuccess);
            result.put("Failure", countFailure);

            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/auth/update-medical-schedule/")
    @CrossOrigin
    public ResponseEntity<?> updateMedicalSchedule(
            @Valid @RequestBody UpdateMedicalScheduleDTO updateMedicalScheduleDTO) {
        try {
            String message = "Có lỗi xảy ra!";
            Optional<MedicalSchedule> medicalScheduleOptional = this.medicalScheduleService
                    .findByMedicalScheduleIdAndActiveTrue(
                            Integer.parseInt(updateMedicalScheduleDTO.getMedicalScheduleId()));

            if (medicalScheduleOptional.isPresent()) {
                MedicalSchedule medicalSchedule = medicalScheduleOptional.get();

                String customTime = updateMedicalScheduleDTO.getCustomTime();
                String startDate = updateMedicalScheduleDTO.getStartDate();
                String medicineName = updateMedicalScheduleDTO.getMedicineName();
                String email = updateMedicalScheduleDTO.getEmail();

                if (customTime != null && !customTime.isEmpty()) {
                    Date customTimeParse = this.dateFormatComponent.myDateTimeFormat().parse(customTime);
                    medicalSchedule.setCustomTime(customTimeParse);
                }

                if (startDate != null && !startDate.isEmpty()) {
                    Date startDateParse = this.dateFormatComponent.myDateFormat().parse(startDate);
                    medicalSchedule.setStartDate(startDateParse);
                }

                if (email != null && !email.isEmpty()) {
                    medicalSchedule.setEmail(email);
                }

                if (medicineName != null && !medicineName.isEmpty()) {
                    medicalSchedule.setMedicineName(medicineName);
                }
                medicalSchedule.setUpdatedDate(new Date());

                return new ResponseEntity<>(this.medicalScheduleService.updateMedicalSchedule(medicalSchedule),
                        HttpStatus.OK);
            } else {
                message = "MedicalSchedule[" + updateMedicalScheduleDTO.getMedicalScheduleId() + "] không tồn tại!";
                return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
            }

        } catch (ParseException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }
}
