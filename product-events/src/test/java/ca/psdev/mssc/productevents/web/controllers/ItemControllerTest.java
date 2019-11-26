package ca.psdev.mssc.productevents.web.controllers;

import ca.psdev.mssc.productevents.documents.Item;
import ca.psdev.mssc.productevents.repo.ItemRepoTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Tag("integration")
@AutoConfigureWebTestClient
class ItemControllerTest {

    @Autowired
    WebTestClient client;

    @Test
    public void getAllItems() {
        client.delete().uri("/v1/items")
                .exchange()
                .expectStatus().isOk();

        client.get().uri("/v1/items")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Item.class)
                .hasSize(0);

        Flux.fromIterable(ItemRepoTest.newItems())
                .subscribe(i -> {
                    client.post().uri("/v1/items")
                            .body(Mono.just(i), Item.class)
                            .accept(MediaType.APPLICATION_JSON)
                            .exchange()
                            .expectStatus().isOk();
                });

        client.get().uri("/v1/items")
                .exchange()
                .expectBodyList(Item.class)
                .consumeWith(r -> {
                    List<Item> items = r.getResponseBody();
                    items.forEach(i -> {
                        assertNotNull(i.getId());
                    });
                });
    }

}