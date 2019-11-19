package ca.psdev.mssc.productevents.web.controllers;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WebFluxTest
@Tag("integration")
class DemoReactiveControllerTest {

    @Autowired
    WebTestClient client;

    @Test
    public void shouldGetFlux() {
        Flux<Integer> f = client.get().uri("/flux")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(Integer.class)
                .getResponseBody();

        StepVerifier.create(f)
                .expectSubscription()
                .expectNext(1, 2, 3)
                .verifyComplete();
    }

    @Test
    public void shouldGetFluxAsList() {
        EntityExchangeResult<List<Integer>> l = client.get().uri("/flux")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Integer.class)
                .returnResult();

        assertEquals(l.getResponseBody(), Arrays.asList(1, 2, 3));
    }

    @Test
    public void shouldReadInfiniteStream() {
        Flux<Long> f = client.get().uri("/infinite")
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(Long.class)
                .getResponseBody();

        StepVerifier.create(f)
                .expectSubscription()
                .expectNext(0l, 1l, 2l, 3l)
                .thenCancel()
                .verify();
    }

    @Test
    public void shouldGetMono() {
        client.get().uri("/mono")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Integer.class)
                .consumeWith(response -> {
                    assertEquals(1, response.getResponseBody());
                });
    }

}