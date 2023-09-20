/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.configs.twilio;

import com.twilio.Twilio;
import com.twilio.exception.ApiException;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Administrator
 */
@Configuration
public class TwilioVerification {

    @Autowired
    TwilioConfiguration twilioConfiguration;

    private final String SUCCESS_STATUS = "approved";
    private final String FAIL_STATUS = "pending";
    private final String VIETNAM_COUNTRY_CODE = "+84";

    public int verification(String phonenumber) {
        try {

            if (this.isPhoneNumberValid(phonenumber)) {
                String VietNam_phonenumber = this.VIETNAM_COUNTRY_CODE + phonenumber.substring(1, phonenumber.length());  
                Verification.creator(
                        twilioConfiguration.getServiceSid(),
                        VietNam_phonenumber,
                        "sms")
                        .create();
                return 1;
            }
            else {
                return 2; // Số điện thoại không hợp lệ
            }
        } catch (ApiException e) {
            return 0;
        }
    }

    public int verification_check(String code, String phoneNumber) {
        try {
            VerificationCheck erificationCheck = VerificationCheck.creator(twilioConfiguration.getServiceSid(), code)
                    .setTo(phoneNumber)
                    .create();

            if (erificationCheck.getStatus().equals(this.SUCCESS_STATUS)) {
                return 1; //Xác thực ok
            } else if (erificationCheck.getStatus().equals(this.FAIL_STATUS)) {
                return 2; // Sai mã xác thực
            }

        } catch (ApiException e) {
            return 3; // Hết hạn mã xác thực
        }
        return 0;
    }

    private boolean isPhoneNumberValid(String phoneNumber) {
        // TODO: Implement phone number validator

        return true;
    }
}
