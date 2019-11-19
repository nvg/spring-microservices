package ca.psdev.mssc.productevents.web.controllers;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
public class DemoReactiveController {

    @GetMapping("/flux")
    public Flux<Integer> getFlux() {
        return Flux.just(1, 2, 3)
                .delayElements(Duration.ofSeconds(1));
    }

    @GetMapping(value = "/infinite", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Long> getStream() {
        return Flux.interval(Duration.ofSeconds(1));
    }

    @GetMapping("/mono")
    public Mono<Integer> getMono() {
        return Mono.just(1);
    }
}
