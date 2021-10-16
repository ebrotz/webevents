package com.brotz.webevents.amq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class TimePublisher {
    @Autowired
    private JmsTemplate jmsTemplate;

    @Scheduled(fixedRate = 1000)
    public void publishTime() {
        long now = Instant.now().toEpochMilli();
        this.jmsTemplate.convertAndSend("topic:time", String.format("{\"time\": %d}", now));
    }
}
