package com.study.webfluxdemo.controlller;

import com.study.webfluxdemo.dto.Response;
import com.study.webfluxdemo.exception.InputValidationException;
import com.study.webfluxdemo.service.ReactiveMathService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("reactive-math")
public class ReactiveMathValidationController {
    private final ReactiveMathService reactiveMathService;

    public ReactiveMathValidationController(ReactiveMathService reactiveMathService) {
        this.reactiveMathService = reactiveMathService;
    }

    @GetMapping("square/{input}/throw")
    public Mono<Response> findSquare(@PathVariable int input) {
        if(input < 10 || input > 20)
            throw new InputValidationException(input);

        return reactiveMathService.findSquare(input);
    }

    @GetMapping("square/{input}/mono-error")
    public Mono<Response> monoError(@PathVariable int input) {
        return Mono.just(input)
                .handle((integer, sink) -> {
                    if(integer >= 10 && integer <= 20)
                        sink.next(integer);
                    else
                        sink.error(new InputValidationException(integer));
                })
                .cast(Integer.class)
                .flatMap(reactiveMathService::findSquare);
    }

    @GetMapping("square/{input}/mono-error-assignment")
    public Mono<ResponseEntity<Response>> monoErrorAssignment(@PathVariable int input) {
        return Mono.just(input)
                .filter(i -> i >= 10 && i <= 20)
                .flatMap(reactiveMathService::findSquare)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }
}
