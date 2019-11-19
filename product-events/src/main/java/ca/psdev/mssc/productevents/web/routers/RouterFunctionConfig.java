package ca.psdev.mssc.productevents.web.routers;

import ca.psdev.mssc.productevents.web.handlers.DemoHandlerFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class RouterFunctionConfig {

    @Bean
    public RouterFunction<ServerResponse> route(DemoHandlerFunction fn) {
        return RouterFunctions.route(
                GET("/fn/flux").and(accept(MediaType.APPLICATION_JSON)), fn::flux
        );
    }
}
