package com.study.webfluxdemo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@Service
@RequiredArgsConstructor
public class CalculatorHandler {
    public Mono<ServerResponse> additionHandler(ServerRequest request) {
        return process(request, (a, b) -> ServerResponse.ok().bodyValue(a + b));
    }

    public Mono<ServerResponse> subtractionHandler(ServerRequest request) {
        return process(request, (a, b) -> ServerResponse.ok().bodyValue(a+b));
    }

    public Mono<ServerResponse> multiplicationHandler(ServerRequest request) {
        return process(request, (a, b) -> ServerResponse.ok().bodyValue(a * b));
    }

    public Mono<ServerResponse> divisionHandler(ServerRequest request) {
        return process(request, (a, b) ->
            b != 0 ? ServerResponse.ok().bodyValue(a / b) :
                    ServerResponse.badRequest().bodyValue("b cannot be 0")
        );
    }

    private Mono<ServerResponse> process(ServerRequest request, BiFunction<Integer, Integer, Mono<ServerResponse>> opLogic) {
        int input1 = getVariable(request, "input1");
        int input2 = getVariable(request, "input2");
        return opLogic.apply(input1, input2);
    }

    private int getVariable(ServerRequest request, String key) {
        return Integer.parseInt(request.pathVariable("key"));
    }
}
