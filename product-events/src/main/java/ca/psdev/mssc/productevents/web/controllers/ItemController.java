package ca.psdev.mssc.productevents.web.controllers;

import ca.psdev.mssc.productevents.documents.Item;
import ca.psdev.mssc.productevents.repo.ItemRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@RestController
public class ItemController {

    @Autowired
    ItemRepo itemRepo;

    @GetMapping("/v1/items")
    public Flux<Item> getAll() {
        return itemRepo.findAll();
    }

    @GetMapping("/v1/items/{id}")
    public Mono<Item> getOne(@PathVariable("id") UUID uuid) {
        return itemRepo.findById(uuid);
    }

    @DeleteMapping("/v1/items")
    public Mono<Void> deleteAll() {
        return itemRepo.deleteAll();
    }

    @PostMapping("/v1/items")
    public Mono<Item> save(@RequestBody Mono<Item> item) {
        return item.flatMap(itemRepo::save);
    }

}
