package com.study.webfluxdemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class Lec09AssignmentTest extends BaseTest {
    private final static String FORMAT = "%d %s %d = %s";
    private final static int A = 10;

    @Autowired
    WebClient webClient;

    @Test
    public void test() {
        var flux = Flux.range(1, 5)
                .flatMap(b -> Flux.just("+", "-", "*", "/")
                        .flatMap(op -> send(b, op))
                )
                .doOnNext(System.out::println)
                .log();

        StepVerifier.create(flux)
                .expectNextCount(20)
                .verifyComplete();

    }

    private Mono<String> send(int b, String op) {
        return webClient
                .get()
                .uri("calculator/{a}/{b}", A, b)
                .headers(h -> h.set("OP", op))
                .retrieve()
                .bodyToMono(String.class)
                .map(v -> String.format(FORMAT, A, op, b, v))
                ;
    }
}
