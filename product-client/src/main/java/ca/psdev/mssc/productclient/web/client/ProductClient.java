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

    protected String getEndpoint() {
        return getEndpoint("");
    }

    protected String getEndpoint(String path) {
        return getApiHost() + PATH_V1 + (path.isEmpty() ? "" : "/") + path;
    }

    public Product getProduct(UUID uuid) {
        ResponseEntity<Product> result = restTemplate.getForEntity(getEndpoint(uuid.toString()), Product.class);
        if (!result.hasBody()) {
            throw new RuntimeException("Unable to fetch product " + uuid);
        }
        return result.getBody();
    }

    public Product newProduct(String productName) {
        Product product = Product.builder().name(productName).build();

        ResponseEntity<Product> result = restTemplate.postForEntity(getEndpoint(), product, Product.class);
        if (!result.hasBody()) {
            throw new RuntimeException("Unable to create product " + productName);
        }
        return result.getBody();
    }

}
