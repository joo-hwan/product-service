package com.study.webfluxdemo;

import com.study.webfluxdemo.dto.MultiplyRequestDto;
import com.study.webfluxdemo.dto.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

@Slf4j
public class Lec08AttributesTest extends BaseTest {
    @Autowired
    WebClient webClient;

    @Test
    public void headersTest() {
        var mono = webClient
                .post()
                .uri("reactive-math/multiply")
                .bodyValue(buildRequestDto(5, 2))
                .attribute("auth", "oauth")
                .retrieve()
                .bodyToMono(Response.class)
                .doOnNext(r -> log.info(r.toString()));

        StepVerifier.create(mono)
                .expectNextCount(1)
                .verifyComplete();

        StepVerifier.create(mono)
                .expectNextCount(1)
                .verifyComplete();
    }

    private MultiplyRequestDto buildRequestDto(int a, int b) {
        MultiplyRequestDto dto = new MultiplyRequestDto();
        dto.setFirst(a);
        dto.setSecond(b);
        return dto;
    }
}
