/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.components.twilio;

import com.tuantran.IMPROOK_CARE.configs.twilio.TwilioConfiguration;
import com.tuantran.IMPROOK_CARE.models.User;
import com.tuantran.IMPROOK_CARE.service.UserService;
import com.twilio.Twilio;
import com.twilio.exception.ApiException;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Administrator
 */
@Component
public class TwilioVerificationComponent {

    @Autowired
    TwilioConfiguration twilioConfiguration;

    @Autowired
    private UserService userService;

    private final String SUCCESS_STATUS = "approved";
    private final String FAIL_STATUS = "pending";
    private final String VIETNAM_COUNTRY_CODE = "+84";

    public int verification(String phonenumber) {
        try {
            if (this.isPhoneNumberValid(phonenumber)) {
                // username nó cũng là phonenumber (Quy ước mới)
                User user = this.userService.findUserByUsername(phonenumber);
                if (user == null) {
                    String VietNam_phonenumber = this.VIETNAM_COUNTRY_CODE + phonenumber.substring(1, phonenumber.length());
                    Verification.creator(
                            twilioConfiguration.getServiceSid(),
                            VietNam_phonenumber,
                            "sms")
                            .create();
                    return 1; // Gửi ok
                } else {
                    return 3; // Số điện thoại đã tồn tại
                }

            } else {
                return 2; // Số điện thoại không hợp lệ
            }
        } catch (ApiException e) {
            return 0;
        }
    }

    public int verification_forgotPassword(String phonenumber) {
        try {
            if (this.isPhoneNumberValid(phonenumber)) {
                // username nó cũng là phonenumber (Quy ước mới)
                User user = this.userService.findUserByUsername(phonenumber);
                if (user != null) {
                    String VietNam_phonenumber = this.VIETNAM_COUNTRY_CODE + phonenumber.substring(1, phonenumber.length());
                    Verification.creator(
                            twilioConfiguration.getServiceSid(),
                            VietNam_phonenumber,
                            "sms")
                            .create();
                    return 1; // Gửi ok
                } else {
                    return 3; // Số điện thoại không tồn tại trong csdl (chưa đăng ký)
                }

            } else {
                return 2; // Số điện thoại không hợp lệ
            }
        } catch (ApiException e) {
            return 0;
        }
    }

    public int verification_check(String code, String phonenumber) {
        try {
            if (this.isPhoneNumberValid(phonenumber)) {
                String VietNam_phonenumber = this.VIETNAM_COUNTRY_CODE + phonenumber.substring(1, phonenumber.length());
                VerificationCheck erificationCheck = VerificationCheck.creator(twilioConfiguration.getServiceSid(), code)
                        .setTo(VietNam_phonenumber)
                        .create();

                if (erificationCheck.getStatus().equals(this.SUCCESS_STATUS)) {
                    return 1; //Xác thực ok
                } else if (erificationCheck.getStatus().equals(this.FAIL_STATUS)) {
                    return 2; // Sai mã xác thực
                }
            } else {
                return 4; // Số điện thoại không hợp lệ
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
