package ca.psdev.mssc.product.order.service.services;

import ca.psdev.mssc.product.order.service.web.model.ProductDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Getter
@Setter
@Component
public class ProductClient {

    public final String PATH_V1 = "/api/v1/products";

    private String apiHost;
    private RestTemplate restTemplate;

    public ProductClient(RestTemplateBuilder templateBuilder) {
        this.restTemplate = templateBuilder.build();
    }

    protected String getEndpoint() {
        return getEndpoint("");
    }

    protected String getEndpoint(String path) {
        return getApiHost() + PATH_V1 + (path.isEmpty() ? "" : "/") + path;
    }

    public ProductDto getProductByUpc(String upc) {
        ResponseEntity<ProductDto> result = restTemplate.getForEntity(getEndpoint("upc/" + upc), ProductDto.class);
        if (!result.hasBody()) {
            throw new RuntimeException("Unable to fetch product for UPC " + upc);
        }
        return result.getBody();
    }
}
