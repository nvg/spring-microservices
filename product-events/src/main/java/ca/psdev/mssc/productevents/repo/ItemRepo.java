package ca.psdev.mssc.productevents.repo;

import ca.psdev.mssc.productevents.documents.Item;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Component
public interface ItemRepo extends ReactiveMongoRepository<Item, UUID> {

    Flux<Item> findByDescription(String description);
}
