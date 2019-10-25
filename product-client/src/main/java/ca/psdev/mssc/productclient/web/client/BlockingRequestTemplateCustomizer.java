package ca.psdev.mssc.productclient.web.client;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class BlockingRequestTemplateCustomizer implements RestTemplateCustomizer {

    public ClientHttpRequestFactory newClientHttpRequestFactory() {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(100);
        cm.setDefaultMaxPerRoute(20);

        RequestConfig rc = RequestConfig
                .custom()
                .setConnectionRequestTimeout(300)
                .setSocketTimeout(300)
                .build();

        CloseableHttpClient hc = HttpClients
                .custom()
                .setConnectionManager(cm)
                .setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())
                .setDefaultRequestConfig(rc)
                .build();

        return new HttpComponentsClientHttpRequestFactory(hc);
    }

    @Override
    public void customize(RestTemplate template) {
        template.setRequestFactory(this.newClientHttpRequestFactory());
    }
}
