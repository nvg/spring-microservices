package ca.psdev.mssc.productservice.repo;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ca.psdev.mssc.productservice.domain.Product;

public interface ProductRepo extends JpaRepository<Product, UUID> {

    Product findByUpc(String upc);
    
    @Query("SELECT p FROM Product p WHERE p.quantity <= p.minQuantity")
    List<Product> findLowStockProducts();

}
