package ca.psdev.mssc.productservice.web.controller;

import ca.psdev.mssc.productservice.mapper.ProductMapper;
import ca.psdev.mssc.productservice.repo.ProductRepo;
import ca.psdev.mssc.productservice.web.model.ProductDto;
import ca.psdev.mssc.productservice.web.model.ProductPage;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Data
@RestController
@RequestMapping("/api/v1/products")
public class ProductsController {

    @Autowired
    private ProductRepo productRepo;

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
}
