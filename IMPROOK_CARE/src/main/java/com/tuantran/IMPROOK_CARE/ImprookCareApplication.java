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
        System.out.println("OK EM");
        User user = new User();
        user.setUsername("abc");
        System.out.println(user.getUsername());
        System.out.println("OpenJDK Version: " + System.getProperty("java.version"));

//        HttpClientBuilder builder = HttpClientBuilder.create();
//        String version = builder.getClass().getPackage().getImplementationVersion();
//        System.out.println("Apache HttpClient Version: " + version);

        String version_Twilio = Twilio.VERSION;
        System.out.println("Twilio SDK Version: " + version_Twilio);
    }
}
