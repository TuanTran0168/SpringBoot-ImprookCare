package com.tuantran.IMPROOK_CARE.configs.twilio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by IntelliJ IDEA. Project : spring-boot-twilio-sms-demo User:
 * hendisantika Email: hendisantika@gmail.com Telegram : @hendisantika34 Date:
 * 23/10/21 Time: 12.53
 */
@Configuration
@ConfigurationProperties("twilio")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TwilioConfiguration {

    @Value("${twilio.account-sid}")
    private String accountSid;
    
    @Value("${twilio.auth-token}")
    private String authToken;

    @Value("${twilio.trial-number}")
    private String trialNumber;
    
    @Value("${twilio.service-sid}")
    private String serviceSid;
}
