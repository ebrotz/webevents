package com.brotz.webevents.sse;

import com.brotz.webevents.sse.dto.TimeMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class TimeReceiver {
    private static final Logger logger = LoggerFactory.getLogger(TimeReceiver.class);

    @Autowired
    private ObjectMapper mapper;

    /**
     * The latest time message received.
     */
    private TimeMessage timeMessage = new TimeMessage(Instant.now().toEpochMilli());

    /**
     * Saves the latest time received.
     * @param time The received time.
     */
    @JmsListener(destination = "topic:time")
    public void receiveTime(String time) {
        TimeMessage timeMessage;

        try {
            timeMessage = mapper.readValue(time, TimeMessage.class);
            logger.debug("Received time " + time.toString());
        } catch (Exception ex) {
            logger.error("Failed to read time message", ex);
            return;
        }

        this.timeMessage = timeMessage;
    }

    public TimeMessage getTimeMessage() {
        return timeMessage;
    }
}
