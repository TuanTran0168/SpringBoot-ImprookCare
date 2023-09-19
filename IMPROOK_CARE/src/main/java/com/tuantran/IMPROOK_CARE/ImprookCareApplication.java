package com.tuantran.IMPROOK_CARE;

import com.tuantran.IMPROOK_CARE.models.User;
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
    }
}
