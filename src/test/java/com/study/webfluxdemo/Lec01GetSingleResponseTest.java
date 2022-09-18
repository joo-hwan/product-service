package com.study.webfluxdemo;

import com.study.webfluxdemo.dto.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

@Slf4j
public class Lec01GetSingleResponseTest extends BaseTest {

    @Autowired
    WebClient webClient;

    @Test
    public void blockTest() {
        var response = webClient.get()
                .uri("reactive-math/square/{number}", 5)
                .retrieve()
                .bodyToMono(Response.class)
                .block();

        log.info(response.toString());
    }

    @Test
    public void stepVerifierTest() {
        var response = webClient.get()
                .uri("reactive-math/square/{number}", 5)
                .retrieve()
                .bodyToMono(Response.class)
                .log();

        StepVerifier.create(response)
                .expectNextMatches(r -> r.getOutput() == 25)
                .verifyComplete();
    }
}
