package com.tuantran.IMPROOK_CARE.dto;

import com.tuantran.IMPROOK_CARE.models.Schedule;
import com.tuantran.IMPROOK_CARE.models.TimeSlot;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Administrator
 */
@Getter
@Setter
public class AddTimeSlotAndScheduleDTO {

    @NotBlank
    private TimeSlot timeSlot;
    @NotBlank
    private Schedule schedule;
}