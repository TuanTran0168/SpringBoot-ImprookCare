/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.controllers;

import com.tuantran.IMPROOK_CARE.components.email.MailService;
import com.tuantran.IMPROOK_CARE.dto.EmailDTO;
import com.tuantran.IMPROOK_CARE.dto.SendCustomEmailDTO;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Administrator
 */
@RestController
@RequestMapping("/api")
public class ApiEmailController {

    @Autowired
    private Environment environment;
    @Autowired
    private MailService mailService;

    @PostMapping("/public/send-form-email/")
    @CrossOrigin
    public ResponseEntity<EmailDTO> sendFormEmail(@RequestBody String mailTo) {
        EmailDTO mail = new EmailDTO();
        mail.setMailFrom("trandangtuan0168@gmail.com");
        mail.setMailTo(mailTo);
        mail.setMailSubject("Chào bạn mình là Tuấn Trần");
        String htmlContent = "<p><b>TEST XÍU NHA:</b></p>\n"
                + "<p>TEST EMAIL HTML!!!! </p>\n"
                + "<table cellpadding=\"8\" cellspacing=\"0\"  width=\"100%\">\n"
                + "   <tr>\n"
                + "        <td style=\"border: 1px solid #EBEDF3;\n"
                + "                       background-color: #f2f2f2 !important;\" width=170>\n"
                + "            username\n"
                + "        </td>\n"
                + "        <td style=\"border: 1px solid #EBEDF3;\">\n"
                + mailTo
                + "        </td>\n"
                + "    </tr>\n"
                + "   \n"
                + "     <tr>\n"
                + "        <td style=\"border: 1px solid #EBEDF3;\n"
                + "                       background-color: #f2f2f2 !important;\">\n"
                + "            password\n"
                + "        </td>\n"
                + "        <td style=\"border: 1px solid #EBEDF3;\">\n"
                + mailTo
                + "        </td>\n"
                + "    </tr>\n"
                + "    <tr>\n"
                + "        <td style=\"border: 1px solid #EBEDF3;\n"
                + "                       background-color: #f2f2f2 !important;\">\n"
                + "            Để đăng nhập\n"
                + "        </td>\n"
                + "        <td style=\"border: 1px solid #EBEDF3; padding-top: 15px; padding-bottom: 15px;\">\n"
                + "            <a href=\"http://localhost:3000/login\" style=\"background-color: #03cb6e; padding: 10px; color: #fff; border-radius: 0.42rem; \">\n"
                + "                <strong>Vui lòng bấm vào đây</strong>\n"
                + "            </a>\n"
                + "        </td>\n"
                + "    </tr>\n"
                + "</table>\n"
                + "<br>\n"
                + "<p>\n"
                + "    Trân trọng,<br><br>\n"
                + "    <b>MAIL JAVA</b><br><br>\n"
                + "    <i>Email này được gửi tự động từ hệ thống Improok Care, vui lòng không phản hồi lại.</i>\n"
                + "</p>";
        mail.setContentType("text/html; charset=utf-8");
        mail.setMailContent(htmlContent);
        this.mailService.sendEmail(mail);

        return new ResponseEntity<>(mail, HttpStatus.OK);
    }

    @PostMapping("/public/send-custom-email/")
    @CrossOrigin
    public ResponseEntity<Map<String, String>> sendCustomEmail(
            @Valid @RequestBody SendCustomEmailDTO sendCustomEmailDTO) {
        Map<String, String> map = new HashMap<>();
        String message = "Có lỗi xảy ra!";

        EmailDTO emailDTO = new EmailDTO();

        emailDTO.setMailFrom(this.environment.getProperty("spring.mail.username"));
        emailDTO.setMailTo(sendCustomEmailDTO.getMailTo());
        emailDTO.setMailSubject(sendCustomEmailDTO.getMailSubject());
        emailDTO.setMailContent(sendCustomEmailDTO.getMailContent());

        map.put("mailFrom", emailDTO.getMailFrom());
        map.put("mailTo", emailDTO.getMailTo());
        map.put("mailCc", emailDTO.getMailCc());
        map.put("mailBcc", emailDTO.getMailBcc());
        map.put("mailSubject", emailDTO.getMailSubject());
        map.put("mailContent", emailDTO.getMailContent());
        map.put("contentType", emailDTO.getContentType());
        map.put("mailSendDate", emailDTO.getMailSendDate().toString());

        int check = this.mailService.sendEmail(emailDTO);

        if (check == 1) {
            message = "Gửi email thành công đến địa chỉ: " + emailDTO.getMailTo();
            map.put("message", message);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } else if (check == 2) {
            message = "Địa chỉ email người nhận không đúng!";
        } else if (check == 0) {
            message = "Gửi email thất bại!";
        }
        map.put("message", message);
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }
}
