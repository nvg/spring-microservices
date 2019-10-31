package ca.psdev.mssc.productservice.web.controller;

import ca.psdev.mssc.productservice.domain.Product;
import ca.psdev.mssc.productservice.mapper.ProductMapper;
import ca.psdev.mssc.productservice.repo.ProductRepo;
import ca.psdev.mssc.productservice.web.model.ProductDto;
import ca.psdev.mssc.productservice.web.model.ProductPage;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Data
@RestController
@RequestMapping("/api/v1/products")
public class ProductsController {

    @Autowired
    private ProductRepo productRepo;

    @GetMapping("/upc/{upc}")
    public ResponseEntity<ProductDto> getByUpc(@Validated @NotNull @PathVariable("upc") String upc) {
        Product product = productRepo.findByUpc(upc);
        return new ResponseEntity<>(ProductMapper.INSTANCE.toProductDto(product), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ProductPage> get(Pageable pageable) {
        List<ProductDto> products = productRepo
                .findAll(pageable)
                .map(ProductMapper.INSTANCE::toProductDto)
                .toList();

        long total = productRepo.count();

        ProductPage result = new ProductPage(products, pageable, total);
        return new ResponseEntity<ProductPage>(result, HttpStatus.OK);
    }


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
    public ResponseEntity<ProductDto> update(@RequestBody @Valid ProductDto productDto) {
        if (productDto == null || productDto.getUuid() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<Product> product = productRepo.findById(productDto.getUuid());
        if (!product.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Product updatedProduct = productRepo.save(ProductMapper.INSTANCE.toProduct(product.get(), productDto));
        return new ResponseEntity<ProductDto>(ProductMapper.INSTANCE.toProductDto(updatedProduct), HttpStatus.OK);
    }
}
