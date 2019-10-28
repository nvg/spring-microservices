package ca.psdev.mssc.productservice.web.controller;

import ca.psdev.mssc.productservice.mapper.ProductMapper;
import ca.psdev.mssc.productservice.repo.ProductRepo;
import ca.psdev.mssc.productservice.web.model.ProductDto;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Data
@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    @Autowired
    private ProductRepo productRepo;

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> get(@PathVariable("id") UUID productId) {
        return productRepo.findById(productId)
                .map(ProductMapper.INSTANCE::toProductDto)
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElse(new ResponseEntity<ProductDto>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<ProductDto> create(@RequestBody ProductDto product) {
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        product.setUuid(UUID.randomUUID());
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ProductDto> update(@RequestBody @Valid ProductDto product) {
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        // TODO Add real implementation
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
}
