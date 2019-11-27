package ca.psdev.mssc.productevents.web.controllers;

import ca.psdev.mssc.productevents.documents.Item;
import ca.psdev.mssc.productevents.repo.ItemRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public Mono<ResponseEntity<Item>> getOne(@PathVariable("id") UUID uuid) {
        return itemRepo.findById(uuid)
                .map(i -> new ResponseEntity<>(i, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/v1/items")
    public Mono<Void> deleteAll() {
        return itemRepo.deleteAll();
    }

    @DeleteMapping("/v1/items/{id}")
    public Mono<ResponseEntity> delete(@PathVariable("id") UUID id) {
        return itemRepo.findById(id)
                .flatMap(i -> {
                    return itemRepo.deleteById(id)
                            .thenReturn(new ResponseEntity(i, HttpStatus.OK));
                }).defaultIfEmpty(new ResponseEntity(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/v1/items/{id}")
    public Mono<ResponseEntity> update(@PathVariable UUID id, @RequestBody Item item) {
        return itemRepo.findById(id)
                .map(i -> {
                    item.setId(id);
                    return itemRepo.save(item);
                }).map(updatedItem -> new ResponseEntity(updatedItem, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/v1/items")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Item> save(@RequestBody Item item) {
        return itemRepo.save(item);
    }

}
