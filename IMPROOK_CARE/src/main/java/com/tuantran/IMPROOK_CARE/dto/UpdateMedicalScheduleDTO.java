package com.tuantran.IMPROOK_CARE.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateMedicalScheduleDTO {
    @NotBlank
    private String medicalScheduleId;
    private String customTime;
    private String startDate;
    private String medicineName;
    private String email;
}
