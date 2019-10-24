package ca.psdev.mssc.productclient.web.client;

import ca.psdev.mssc.productservice.web.model.Product;
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
@ConfigurationProperties(value = "sfg.client", ignoreUnknownFields = false)
public class ProductClient {

    public final String PATH_V1 = "/api/v1/product";

    private String apiHost;
    private RestTemplate restTemplate;

    public ProductClient(RestTemplateBuilder templateBuilder) {
        this.restTemplate = templateBuilder.build();
    }

    public Product getProduct(UUID uuid) {
        ResponseEntity<Product> result = restTemplate.getForEntity(getApiHost() + PATH_V1 + "/" + uuid, Product.class);
        if (!result.hasBody()) {
            throw new RuntimeException("Unable to fetch product " + uuid);
        }
        return result.getBody();
    }

}
