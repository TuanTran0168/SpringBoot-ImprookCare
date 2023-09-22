package com.tuantran.IMPROOK_CARE;

import com.twilio.Twilio;
import java.util.Date;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ImprookCareApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImprookCareApplication.class, args);

        System.out.println("OpenJDK Version: " + System.getProperty("java.version"));
        System.out.println("Twilio SDK Version: " + Twilio.VERSION);
        System.out.println("RUN COMPLETED AT: " + new Date());
        System.out.println("OK EM");
    }
}
