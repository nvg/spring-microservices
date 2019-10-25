package ca.psdev.mssc.productservice.web.controller;

import ca.psdev.mssc.productservice.web.model.ProductDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void shouldCompleteGetOperation() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/product/" + UUID.randomUUID()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void create() throws Exception {
        ProductDto product = ProductDto.builder().build();
        String productJson = objectMapper.writeValueAsString(product);

        mockMvc.perform(post("/api/v1/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productJson))
                .andExpect(status().isCreated());
    }

    @Test
    void update() throws Exception {
        ProductDto product = ProductDto.builder().build();
        String productJson = objectMapper.writeValueAsString(product);

        mockMvc.perform(put("/api/v1/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productJson))
                .andExpect(status().isOk());
    }
}