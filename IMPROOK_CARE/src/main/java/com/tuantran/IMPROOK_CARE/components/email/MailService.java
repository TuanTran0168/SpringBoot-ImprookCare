package com.tuantran.IMPROOK_CARE.components.email;

import com.tuantran.IMPROOK_CARE.dto.EmailDTO;

public interface MailService {

    public int sendEmail(EmailDTO mail);
}
