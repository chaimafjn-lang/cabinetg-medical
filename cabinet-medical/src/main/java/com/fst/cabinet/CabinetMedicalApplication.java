package com.fst.cabinet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure
    .SpringBootApplication;
import org.springframework.scheduling.annotation
    .EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CabinetMedicalApplication {
    public static void main(String[] args) {
        SpringApplication.run(
            CabinetMedicalApplication.class, args);
    }
}

