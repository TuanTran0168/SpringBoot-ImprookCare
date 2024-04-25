package com.tuantran.IMPROOK_CARE.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTestResultDTO {

    @NotBlank
    private String testResultId;

    private String userId; // Kết quả test được thực hiện bởi user nào

    private String testResultValue;

    private String testResultDiagnosis;

    private String testServiceId;

}
