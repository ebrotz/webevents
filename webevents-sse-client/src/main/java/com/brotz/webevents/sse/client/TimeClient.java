package com.brotz.webevents.sse.client;

import com.brotz.webevents.sse.dto.TimeMessage;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class TimeClient {
    private WebClient webClient;

    public TimeClient(WebClient.Builder builder, String baseUrl) {
        this.webClient = builder.baseUrl(baseUrl).build();
    }

    public Mono<Long> getMessage() {
        return this.webClient.get().uri("/time").accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(TimeMessage.class)
                .map(TimeMessage::getTime);
    }
}
