package com.tuantran.IMPROOK_CARE.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-twilio-sms-demo
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 23/10/21
 * Time: 12.57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmsRequestDTO {

    @JsonProperty("phoneNumber")
    @NotBlank
    private String phoneNumber; // destination

    @JsonProperty("message")
    @NotBlank
    private String message;

}
