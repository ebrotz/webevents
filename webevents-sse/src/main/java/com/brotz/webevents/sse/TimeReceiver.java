package com.brotz.webevents.sse;

import com.brotz.webevents.sse.dto.TimeMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class TimeReceiver {
    private static final Logger logger = LoggerFactory.getLogger(TimeReceiver.class);

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private SseRegistry<TimeMessage> sseRegistry;

    @Autowired
    private TaskExecutor taskExecutor;

    /**
     * Saves the latest time received.
     * @param time The received time.
     */
    @JmsListener(destination = "topic:time")
    public void receiveTime(String time) {
        // Get off the listener thread.
        taskExecutor.execute(() -> {
            TimeMessage timeMessage;
            try {
                timeMessage = mapper.readValue(time, TimeMessage.class);
                logger.trace("Received time " + time);
            } catch (Exception ex) {
                logger.error("Failed to read time message", ex);
                return;
            }

            sseRegistry.send(timeMessage);
        });
    }
}
