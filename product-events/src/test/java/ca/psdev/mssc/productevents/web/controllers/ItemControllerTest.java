package ca.psdev.mssc.productevents.web.controllers;

import ca.psdev.mssc.productevents.documents.Item;
import ca.psdev.mssc.productevents.repo.ItemRepoTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Tag("integration")
@DirtiesContext
@AutoConfigureWebTestClient
@Slf4j
class ItemControllerTest {

    @Autowired
    WebTestClient client;

    @BeforeEach
    public void setup() {
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
                            .expectStatus().isCreated();
                });
    }

    @Test
    public void getAllItems() {
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

    @Test
    public void shouldDelete() {
        client.get().uri("/v1/items")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Item.class)
                .consumeWith(r -> {
                    List<Item> items = r.getResponseBody();
                    assertTrue(items.size() > 0);

                    Item itemToDelete = items.get(0);
                    client.delete().uri("/v1/items/" + itemToDelete.getId())
                            .exchange()
                            .expectStatus().isOk()
                            .expectBody(Item.class)
                            .consumeWith(resp -> {
                                Item deletedItem = resp.getResponseBody();

                                client.get().uri("/v1/items/" + deletedItem.getId())
                                        .exchange()
                                        .expectStatus().isNotFound();
                            });
                });
    }

    @Test
    public void shouldNotFindOneItem() {
        client.get().uri("/v1/items/cafebabe-0000-0000-0000-000000000000")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    public void shouldUpdateAnItem() {
        client.get().uri("/v1/items")
                .exchange()
                .expectBodyList(Item.class)
                .consumeWith(r -> {
                    List<Item> items = r.getResponseBody();
                    assertTrue(items.size() > 0);
                    final Item item = items.get(0);
                    item.setDescription("UPDATED ITEM");
                    item.setPrice(Math.random() * 10000);

                    client.put().uri("/v1/items/" + item.getId())
                            .bodyValue(item)
                            .exchange()
                            .expectBody(Item.class)
                            .consumeWith(rr -> {
                                Item i = rr.getResponseBody();
                                assertEquals(item, i);

                                client.get().uri("/v1/items/" + item.getId())
                                        .exchange()
                                        .expectStatus().isOk()
                                        .expectBody(Item.class)
                                        .consumeWith(newGetItem -> {
                                            assertEquals(newGetItem.getResponseBody(), item);
                                        });
                            });
                });

    }

    @Test
    public void shouldGetOneItem() {
        client.get().uri("/v1/items")
                .exchange()
                .expectBodyList(Item.class)
                .consumeWith(r -> {
                    List<Item> items = r.getResponseBody();
                    assertTrue(items.size() > 0);
                    final Item item = items.get(0);

                    client.get().uri("/v1/items/" + item.getId())
                            .exchange()
                            .expectBody(Item.class)
                            .consumeWith(rr -> {
                                Item i = rr.getResponseBody();
                                assertEquals(item, i);
                            });
                });
    }

}