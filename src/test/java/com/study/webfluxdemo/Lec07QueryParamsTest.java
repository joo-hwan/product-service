package com.study.webfluxdemo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.test.StepVerifier;

import java.util.Map;

@Slf4j
public class Lec07QueryParamsTest extends BaseTest{
    @Autowired
    WebClient webClient;

    String queryString = "http://localhost:8080/jobs/search?count={count}&page={page}";

    @Test
    public void queryParamsTest() {
        /*
        var uri = UriComponentsBuilder.fromUriString(queryString)
                .build(10, 20);
         */

        var m = Map.of(
                "count", 10,
                "page", 20
        );

        var res = webClient.get()
                .uri(b -> b.path("jobs/search").query("count={count}&page={page}").build(m))
                .retrieve()
                .bodyToFlux(Integer.class)
                .log();

        StepVerifier.create(res)
                .expectNextCount(2)
                .verifyComplete();
    }
}
