package com.brotz.webevents.sse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Mono;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
public class TimeController {

    @Autowired
    private TimeReceiver timeReceiver;

    @Autowired
    private SseRegistry registry;

    @GetMapping(path = "/time")
    public SseEmitter getTime(@RequestParam String id) {
        return registry.getEmitter(id);
    }

    @DeleteMapping(path = "/done")
    public void done(@RequestParam String id) {
        registry.removeEmitter(id);
    }
}
