package com.tuantran.IMPROOK_CARE.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTimeSlotDTO {
    @NotBlank
    private String timeSlotId;
    @NotBlank
    String timeBegin;
    @NotBlank
    String timeEnd;
    @NotBlank
    String note;
}
