package com.study.webfluxdemo;

import com.study.webfluxdemo.dto.InputFailedValidationResponse;
import com.study.webfluxdemo.dto.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@Slf4j
public class Lec06ExchangeTest extends BaseTest {
    @Autowired
    WebClient webClient;

    // exchange = retrieve + additional info http info status code
    @Test
    public void stepVerifierTest() {
        var response = webClient.get()
                .uri("reactive-math/square/{number}/throw", 5)
                .exchangeToMono(this::exchange)
                .doOnNext(r -> log.info(r.toString()))
                .doOnError(err -> log.info(err.getMessage()))
                .log();

        StepVerifier.create(response)
                .verifyError(WebClientResponseException.BadRequest.class);
    }

    private Mono<Object> exchange(ClientResponse clientResponse) {
        if(clientResponse.rawStatusCode() == 400) {
            return clientResponse.bodyToMono(InputFailedValidationResponse.class);
        }
        else
            return clientResponse.bodyToMono(Response.class);
    }
}
