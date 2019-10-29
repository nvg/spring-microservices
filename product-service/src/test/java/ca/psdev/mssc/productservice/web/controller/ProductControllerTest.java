package ca.psdev.mssc.productservice.web.controller;

import ca.psdev.mssc.productservice.domain.Product;
import ca.psdev.mssc.productservice.repo.ProductRepo;
import ca.psdev.mssc.productservice.web.model.ProductDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs(uriHost = "product-service-demo")
@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ProductRepo productRepo;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void shouldCompleteGetOperation() throws Exception {
        UUID uuid = UUID.randomUUID();
        given(productRepo.findById(uuid)).willReturn(Optional.of(Product.builder().uuid(uuid).build()));

        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/product/{productId}", uuid)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcRestDocumentation.document("v1/product-get", pathParameters(
                        parameterWithName("productId").description("Identifier of the product to retrieve")
                )));
    }

    @Test
    void create() throws Exception {
        ProductDto product = ProductDto.builder().build();
        String productJson = objectMapper.writeValueAsString(product);

        mockMvc.perform(RestDocumentationRequestBuilders.post("/api/v1/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productJson))
                .andExpect(status().isCreated())
                .andDo(MockMvcRestDocumentation.document("v1/product-post"));
    }

    @Test
    void update() throws Exception {
        ProductDto product = ProductDto.builder()
                .name("Demo")
                .build();
        String productJson = objectMapper.writeValueAsString(product);

        mockMvc.perform(RestDocumentationRequestBuilders.put("/api/v1/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productJson))
                .andExpect(status().isOk())
                .andDo(MockMvcRestDocumentation.document("v1/product-update"));
    }
}