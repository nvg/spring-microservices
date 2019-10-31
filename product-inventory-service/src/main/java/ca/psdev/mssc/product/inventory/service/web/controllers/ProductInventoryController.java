package ca.psdev.mssc.product.inventory.service.web.controllers;

import ca.psdev.mssc.product.inventory.service.repositories.ProductInventoryRepository;
import ca.psdev.mssc.product.inventory.service.web.mappers.ProductInventoryMapper;
import ca.psdev.mssc.product.inventory.service.web.model.ProductInventoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ProductInventoryController {

    private final ProductInventoryRepository productInventoryRepository;
    private final ProductInventoryMapper productInventoryMapper;

    @GetMapping("api/v1/product/{productId}/inventory")
    List<ProductInventoryDto> listProductsById(@PathVariable UUID productId){
        log.debug("Finding Inventory for productId:" + productId);

        return productInventoryRepository.findAllByProductId(productId)
                .stream()
                .map(productInventoryMapper::productInventoryToProductInventoryDto)
                .collect(Collectors.toList());
    }
}
