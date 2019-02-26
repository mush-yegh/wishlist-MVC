package com.wishlist.app;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableScheduling
@SpringBootApplication
@ComponentScan(basePackages = "com.wishlist")
@EntityScan(basePackages = "com.wishlist.persistance.entity")
@EnableJpaRepositories(basePackages = "com.wishlist.persistance.repository")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        System.out.println("Welcome");
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
