package ca.psdev.mssc.productservice.web.controller;

import ca.psdev.mssc.productservice.domain.Product;
import ca.psdev.mssc.productservice.repo.ProductRepo;
import ca.psdev.mssc.productservice.web.model.ProductDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductsController.class)
class ProductsControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ProductRepo productRepo;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void shouldCompleteGetOperation() throws Exception {
        UUID uuid = UUID.randomUUID();

        List<Product> prods = new ArrayList<>();
        prods.add(Product.builder().uuid(uuid).build());
        Page<Product> page = new PageImpl<>(prods);
        // alternatively can use doReturn(page).when(productRepo).findAll(any(Page.class));
        given(productRepo.findAll(any(Pageable.class))).willReturn(page);
        given(productRepo.count()).willReturn(1l);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}