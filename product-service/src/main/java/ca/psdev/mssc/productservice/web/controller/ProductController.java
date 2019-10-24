package ca.psdev.mssc.productservice.web.controller;

import ca.psdev.mssc.productservice.web.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    @GetMapping("/{id}")
    public ResponseEntity<Product> get(@PathVariable("id") UUID productId) {
        // TODO Add real implementation
        return new ResponseEntity<>(Product.builder().uuid(productId).build(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product product) {
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        product.setUuid(UUID.randomUUID());
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Product> update(@RequestBody Product product) {
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        // TODO Add real implementation
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
}






