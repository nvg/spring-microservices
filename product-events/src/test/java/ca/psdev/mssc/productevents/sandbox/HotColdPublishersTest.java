package ca.psdev.mssc.productevents.sandbox;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.test.scheduler.VirtualTimeScheduler;

import java.time.Duration;

public class HotColdPublishersTest {

    @Test
    public void coldPublishersTest() throws Exception {
        // all subscribers get the full set of elements
        Flux<String> f = Flux.just("A", "B", "C", "D")
                .delayElements(Duration.ofSeconds(1));
        f.subscribe(s -> System.out.println("Sub1: " + s));
        Thread.sleep(1000);
        f.subscribe(s -> System.out.println("Sub2: " + s));
        Thread.sleep(1000);
        f.subscribe(s -> System.out.println("Sub3: " + s));
        Thread.sleep(4000);
    }

    @Test
    public void hotPublishersTest() throws Exception {
        // subscribers get the element based on the subscription time
        Flux<String> f = Flux.just("A", "B", "C", "D")
                .delayElements(Duration.ofSeconds(1));

        ConnectableFlux<String> cf = f.publish();
        cf.connect();

        cf.subscribe(s -> System.out.println("Sub1: " + s));
        Thread.sleep(2000);
        cf.subscribe(s -> System.out.println("Sub2: " + s)); // will only receive elements after subscription
        Thread.sleep(4000);
    }

}
