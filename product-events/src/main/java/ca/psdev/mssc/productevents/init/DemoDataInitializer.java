package ca.psdev.mssc.productevents.init;

import ca.psdev.mssc.productevents.documents.Item;
import ca.psdev.mssc.productevents.repo.ItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Profile("dev")
public class DemoDataInitializer implements CommandLineRunner {

    @Autowired
    ItemRepo repo;

    @Override
    public void run(String... args) throws Exception {
        repo.deleteAll()
                .thenMany(Flux.fromIterable(newItems()))
                .flatMap(repo::save)
                .thenMany(repo.findAll())
                .subscribe(i -> {
                    System.out.println(i);
                });
    }

    public List<Item> newItems() {
        List<Item> result = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Item item = Item.builder().id(UUID.randomUUID())
                    .description("Demo Item " + i)
                    .price(Math.random() * i * 10)
                    .build();
            result.add(item);
        }
        return result;
    }

}
