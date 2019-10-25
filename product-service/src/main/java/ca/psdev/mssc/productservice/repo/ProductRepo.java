package ca.psdev.mssc.productservice.repo;

import ca.psdev.mssc.productservice.domain.Product;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface ProductRepo extends PagingAndSortingRepository<Product, UUID> {
}
