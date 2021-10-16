package com.brotz.webevents.sse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SseRegistry<T> {
    private static final Logger logger = LoggerFactory.getLogger(SseRegistry.class);
    private final Map<String, SseEmitter> sseEmitters = new HashMap<>();

    public SseEmitter getEmitter(String id) {
        SseEmitter emitter;

        synchronized (sseEmitters) {
            if (!sseEmitters.containsKey(id)) {
                SseEmitter e = new SseEmitter();
                sseEmitters.put(id, e);
            }

            emitter = sseEmitters.get(id);
        }

        logger.debug(String.format("Created emitter %s", id));
        return emitter;
    }

    public void removeEmitter(String id){
        SseEmitter emitter;
        synchronized (sseEmitters) {
            emitter = sseEmitters.remove(id);
        }

        if (emitter != null) {
            logger.debug(String.format("Removed emitter %s", id));
            emitter.complete();
        }
    }

    /**
     * Sends an object to all registered emitters.
     * @param t The object to send to the emitters.
     */
    public void send(T t) {
        List<String> failedEmitters = new ArrayList<>();

        synchronized (sseEmitters) {
            for (String id: sseEmitters.keySet()) {
                SseEmitter emitter = sseEmitters.get(id);
                try {
                    emitter.send(t, MediaType.APPLICATION_JSON);
                } catch (Exception ex) {
                    logger.error(String.format("Failed to send to SseEmitter %s", id));
                    failedEmitters.add(id);
                }
            }

            failedEmitters.forEach(i -> removeEmitter(i));
        }
    }
}
