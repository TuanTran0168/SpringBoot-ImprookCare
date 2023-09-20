package com.tuantran.IMPROOK_CARE.components.twilio;

import com.tuantran.IMPROOK_CARE.dto.SmsRequestDTO;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-twilio-sms-demo
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 23/10/21
 * Time: 12.57
 */
public interface SmsSender {

    void sendSms(SmsRequestDTO smsRequest);

    // or maybe void sendSms(String phoneNumber, String message);
}
