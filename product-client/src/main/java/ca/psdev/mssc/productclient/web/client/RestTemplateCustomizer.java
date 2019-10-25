package ca.psdev.mssc.productclient.web.client;

import org.springframework.web.client.RestTemplate;

@FunctionalInterface
public interface RestTemplateCustomizer {

    void customize(RestTemplate template);

}
