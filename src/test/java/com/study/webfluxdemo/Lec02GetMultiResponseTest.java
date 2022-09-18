package com.study.webfluxdemo;

import com.study.webfluxdemo.dto.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

@Slf4j
public class Lec02GetMultiResponseTest extends BaseTest {

    @Autowired
    WebClient webClient;

    @Test
    public void stepVerifierTest() {
        var response = webClient.get()
                .uri("reactive-math/table/{number}", 5)
                .retrieve()
                .bodyToFlux(Response.class)
                .doOnNext(r -> log.info(r.toString()))
                .log();

        StepVerifier.create(response)
                .expectNextCount(10)
                .verifyComplete();
    }

    @Test
    public void fluxStreamTest() {
        var response = webClient.get()
                .uri("reactive-math/table/{number}/stream", 5)
                .retrieve()
                .bodyToFlux(Response.class)
                .doOnNext(r -> log.info(r.toString()))
                .log();

        StepVerifier.create(response)
                .expectNextCount(10)
                .verifyComplete();
    }
}
