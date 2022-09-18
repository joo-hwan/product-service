package com.study.webfluxdemo.config;

import com.study.webfluxdemo.exception.InputValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;

@Configuration
public class CaculationRouterConfig {

    @Autowired
    CalculatorHandler calculatorHandler;

    @Bean
    public RouterFunction<ServerResponse> calculationRouter() {
        return RouterFunctions.route()
                .path("caculator", this::serverResponseRouterFunction)
                .build();
    }

    private RouterFunction<ServerResponse> serverResponseRouterFunction() {
        return RouterFunctions.route()
                .GET("{input1}/{input2}", isOperation("+"), calculatorHandler::additionHandler)
                .GET("{input1}/{input2}", isOperation("-"), calculatorHandler::subtractionHandler)
                .GET("{input1}/{input2}", isOperation("*"), calculatorHandler::multiplicationHandler)
                .GET("{input1}/{input2}", isOperation("/"), calculatorHandler::divisionHandler)
                .GET("{input1}/{input2}", req -> ServerResponse.badRequest().bodyValue("OP should be ~"))
                .build();
    }

    private RequestPredicate isOperation(String operation) {
        return RequestPredicates.headers(headers -> operation.equals(headers.asHttpHeaders()
                .toSingleValueMap().get("OP")));
    }
}
