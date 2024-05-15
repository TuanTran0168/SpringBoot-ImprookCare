package com.tuantran.IMPROOK_CARE.dto;

import java.util.List;

import com.tuantran.IMPROOK_CARE.models.PrescriptionDetail;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrescriptionDetailReminderDTO {
    PrescriptionDetail prescriptionDetail;
    List<MedicalReminderDTO> timeReminders;
    // List<MedicalReminder> timeReminders;
}
