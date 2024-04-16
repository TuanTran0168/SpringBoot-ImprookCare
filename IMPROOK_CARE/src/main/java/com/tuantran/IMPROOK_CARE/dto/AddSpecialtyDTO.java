package com.tuantran.IMPROOK_CARE.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddSpecialtyDTO {
    @NotBlank
    String specialtyName;
    @NotBlank
    String description;
}
