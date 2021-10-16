package com.brotz.webevents.sse;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class TimeReceiver {
    @JmsListener(destination = "topic:time")
    public void receiveTime(String time) {
        System.out.println(String.format("Received %s", time));
    }
}
