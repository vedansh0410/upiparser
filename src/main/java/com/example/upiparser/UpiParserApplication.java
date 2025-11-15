package com.example.upiparser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,                 // Disable DB
        SecurityAutoConfiguration.class,                  // Disable Spring Security
        ManagementWebSecurityAutoConfiguration.class      // Disable Actuator Security
})
public class UpiParserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UpiParserApplication.class, args);
    }
}
