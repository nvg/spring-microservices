package ca.psdev.mssc.productevents.repo;

import ca.psdev.mssc.productevents.documents.Item;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@DataMongoTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@Slf4j
public class ItemRepoTest {

    private static List<Item> items;

    @Autowired
    ItemRepo repo;

    @Test
    @DisplayName("Given a list of new items" +
            "When saving items" +
            "Then saved count should be the same as number of the retrieved count")
    public void shouldFetchAllItems() {
        Flux<Item> f = repo.findAll();
        StepVerifier.create(f)
                .expectSubscription()
                .expectNextCount(newItems().size())
                .verifyComplete();
    }

    public void shouldGetByDescription() {
        String descr = items.get(0).getDescription();

        StepVerifier.create(repo.findByDescription(descr))
                .expectSubscription()
                .expectNextMatches(i -> i.getDescription().equals(descr))
                .verifyComplete();
    }

    @Test
    public void shouldGetItemById() {
        UUID itemId = items.get(0).getId();

        StepVerifier.create(repo.findById(itemId))
                .expectSubscription()
                .expectNextMatches(i -> i.getId().equals(itemId))
                .verifyComplete();
    }

    @BeforeEach
    public void setup() {
        repo.deleteAll()
                .thenMany(Flux.fromIterable(newItems()))
                .flatMap(repo::save)
                .doOnNext(i -> {
                            log.info("Inserted " + i);
                        }
                ).blockLast();

    }

    @Test
    public void shouldSaveAnItem() {
        Item i = Item.builder()
                .id(UUID.randomUUID())
                .description("ThinkPad X490")
                .price(999.00)
                .build();

        Mono<Item> savedItem = repo.save(i);
        StepVerifier.create(savedItem)
                .expectSubscription()
                .expectNextMatches(item -> item.getId() != null && item.getDescription().equals(i.getDescription()))
                .verifyComplete();
    }

    @Test
    public void shouldUpdateAnItem() {
        Flux<Item> updatedItem = repo.findByDescription("Demo Item 1")
                .map(i -> {
                    i.setDescription("UPDATED");
                    i.setPrice(99.00);
                    return i;
                }).flatMap(i -> {
                    return repo.save(i);
                });

        StepVerifier.create(updatedItem)
                .expectSubscription()
                .expectNextMatches(i -> i.getPrice() == 99.00 && i.getDescription().equals("UPDATED"))
                .verifyComplete();
    }

    @Test
    public void shouldDeleteAnItem() {
        Mono<Void> i = repo.deleteById(items.get(0).getId());

        StepVerifier.create(i)
                .expectSubscription()
                .verifyComplete();

    }

    public static List<Item> newItems() {
        if (items != null) {
            return items;
        }

        List<Item> result = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Item item = Item.builder().id(UUID.randomUUID())
                    .description("Demo Item " + i)
                    .price(Math.random() * i * 10)
                    .build();
            result.add(item);
        }
        items = result;
        return items;
    }

}