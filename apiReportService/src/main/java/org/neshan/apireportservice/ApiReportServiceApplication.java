package org.neshan.apireportservice;

import jakarta.annotation.PostConstruct;
import org.neshan.apireportservice.entity.User;
import org.neshan.apireportservice.entity.model.enums.Role;
import org.neshan.apireportservice.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiReportServiceApplication {

    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(ApiReportServiceApplication.class, args);
    }

    @PostConstruct
    public void initUser() {

        User user = new User();
        user.setRole(Role.USER);
        user.setTrust(1111);
        user.setUsername("yasin");
        user.setPassword("  ");
        userRepository.save(user);

        User user2 = new User();
        user2.setRole(Role.OPERATOR);
        user2.setTrust(1111);
        user2.setUsername("yasin_op");
        user2.setPassword("  ");
        userRepository.save(user2);

        User user3 = new User();
        user3.setRole(Role.USER);
        user3.setTrust(0.1f);
        user3.setUsername("yasin_no_trust");
        user3.setPassword("  ");
        userRepository.save(user3);


        User user4 = new User();
        user4.setRole(Role.ADMIN);
        user4.setTrust(0);
        user4.setUsername("yasin_admin");
        user4.setPassword("  ");
        userRepository.save(user4);

    }
}
