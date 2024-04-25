package com.tuantran.IMPROOK_CARE.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReturnTestResultDTO {
    @NotBlank
    private String testResultId;

    @NotBlank
    private String userId; // Kết quả test được thực hiện bởi user nào

    @NotBlank
    private String testResultValue;

    @NotBlank
    private String testResultDiagnosis;
}
