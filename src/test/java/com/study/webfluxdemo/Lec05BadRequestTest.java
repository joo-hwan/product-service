package com.study.webfluxdemo;

import com.study.webfluxdemo.dto.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.test.StepVerifier;

@Slf4j
public class Lec05BadRequestTest extends BaseTest {
    @Autowired
    WebClient webClient;

    @Test
    public void stepVerifierTest() {
        var response = webClient.get()
                .uri("reactive-math/square/{number}/throw", 5)
                .retrieve()
                .bodyToMono(Response.class)
                .doOnNext(r -> log.info(r.toString()))
                .doOnError(err -> log.info(err.getMessage()))
                .log();

        StepVerifier.create(response)
                .verifyError(WebClientResponseException.BadRequest.class);
    }
}
