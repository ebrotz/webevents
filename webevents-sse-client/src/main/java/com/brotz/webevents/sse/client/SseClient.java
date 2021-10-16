package com.brotz.webevents.sse.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class SseClient {
    @Autowired
    WebClient.Builder builder;

    @Bean
    public TimeClient timeClient() {
        return new TimeClient(builder, "http://localhost:8080");
    }

    public static void main(String[] args){
        ConfigurableApplicationContext context = SpringApplication.run(SseClient.class, args);
        TimeClient timeClient = context.getBean(TimeClient.class);
        System.out.println(timeClient.getMessage().block());
    }
}
