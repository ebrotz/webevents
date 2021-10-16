package com.brotz.webevents.amq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AmqPublisher {
    public static void main (String[] args) {
        SpringApplication.run(AmqPublisher.class);
    }
}
