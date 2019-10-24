package ca.psdev.mssc.productclient.web.client;

import ca.psdev.mssc.productservice.web.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductClientTest {

    @Autowired
    ProductClient productClient;

    @Test
    void shouldRetrieveAProduct() {
        Product product = productClient.getProduct(UUID.randomUUID());
        assertNotNull(product);
    }

    @Test
    void shouldCreateAProduct() {
        Product product = productClient.newProduct("Demo Product");
        assertNotNull(product);
        assertNotNull(product.getUuid());
        assertEquals("Demo Product", product.getName());
    }
}