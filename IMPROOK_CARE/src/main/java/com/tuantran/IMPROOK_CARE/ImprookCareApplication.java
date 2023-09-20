package com.tuantran.IMPROOK_CARE;

import com.tuantran.IMPROOK_CARE.models.User;
import com.twilio.Twilio;
//import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ImprookCareApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImprookCareApplication.class, args);

        System.out.println("OpenJDK Version: " + System.getProperty("java.version"));
        String version_Twilio = Twilio.VERSION;
        System.out.println("Twilio SDK Version: " + version_Twilio);
        System.out.println("OK EM");
    }
}
