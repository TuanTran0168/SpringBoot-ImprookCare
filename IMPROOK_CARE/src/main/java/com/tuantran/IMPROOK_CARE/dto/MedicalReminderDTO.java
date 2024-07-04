package com.tuantran.IMPROOK_CARE.dto;

import com.tuantran.IMPROOK_CARE.models.TimeReminder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicalReminderDTO {
    private String medicalReminderId;
    private String prescriptionDetailId;
    private TimeReminder timeReminderId;
}
