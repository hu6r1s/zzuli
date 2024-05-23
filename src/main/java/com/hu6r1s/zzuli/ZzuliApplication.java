package com.hu6r1s.zzuli;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class ZzuliApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZzuliApplication.class, args);
    }

}
