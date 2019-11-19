package ca.psdev.mssc.productevents.sandbox;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static reactor.core.scheduler.Schedulers.parallel;

@Slf4j
public class FluxAndMonoTest {

    private List<String> strings = Arrays.asList("String1", "String2", "String3", "String4");

    @Test
    public void demoFluxTest() {
        Flux<String> sf = Flux.fromIterable(strings)
                .concatWith(Flux.error(new RuntimeException("Error occurred")))
                .concatWith(Flux.just("String5", "String6", "String7", "String8"));

        Consumer<String> onNextConsumer = System.out::println;
        Consumer<Throwable> onErrorConsumer = e -> System.out.println(e.getMessage());
        Runnable onCompleteConsumer = () -> System.out.println("Complete"); // won't run due to exception

        sf.subscribe(onNextConsumer, onErrorConsumer, onCompleteConsumer);
    }


    @Test
    public void shouldWorkWithFluxExceptions() {
        Flux<String> sf = Flux.just("String1").concatWith(Flux.error(new Exception("Bang!")));

        StepVerifier.create(sf)
                .expectNextMatches(s -> true)
                .expectError(Exception.class)
                // or .expectErrorMessage("Bang!")
                .verify();
    }

    @Test
    public void shouldRespectFluxOrder() {
        Flux<String> sf = Flux.just("String1", "String2", "String3", "String4");

        StepVerifier.create(sf)
                .expectNext("String1")
                .expectNext("String2")
                .expectNext("String3")
                .expectNext("String4")
                .verifyComplete();
    }

    @Test
    public void shouldFilterFluxStream() {
        Flux<String> sf = Flux.fromIterable(strings)
                .log("Before filter")
                .filter(s -> s.endsWith("4"))
                .log("After filter");

        StepVerifier.create(sf)
                .expectNext("String4")
                .verifyComplete();
    }

    @Test
    public void demoMonoTest() {
        StepVerifier.create(Mono.just("Demo"))
                .expectNext("Demo")
                .verifyComplete();
    }

    @Test
    public void shouldTransformInParralelUsingFlatMap() {
        long time = System.currentTimeMillis();

        Flux<String> sf = Flux.fromIterable(strings)
                .window(2) // wait for 2 elements before passing to the next step
                .flatMap((s) ->
                        s.map(this::toList).subscribeOn(parallel()))
                .flatMap(s -> Flux.fromIterable(s))
                // .log()
                ;

        StepVerifier.create(sf)
                .expectNextCount(strings.size() * 2)
                .verifyComplete();

        log.info("Parallel completed in " + (System.currentTimeMillis() - time));

        time = System.currentTimeMillis();

        sf = Flux.fromIterable(strings)
                .map(s -> toList(s))
                .flatMap(s -> Flux.fromIterable(s))
        // .log()
        ;

        StepVerifier.create(sf)
                .expectNextCount(strings.size() * 2)
                .verifyComplete();

        log.info("Sequential completed in " + (System.currentTimeMillis() - time));
    }

    @Test
    public void shouldHandleExceptions() {
        Flux<String> f1 = Flux.fromIterable(strings)
                .concatWith(Flux.error(new RuntimeException("Bang!")))
                .concatWith(Flux.just("String5"))
                .onErrorResume((e) -> {
                    log.info("Got an error", e);
                    return Flux.just("default");
                });

        StepVerifier.create(f1)
                .expectNext("String1", "String2", "String3", "String4", "default")
                .verifyComplete();
    }

    @Test
    public void shouldWorkWithMergedFluxes() {
        Flux<String> f1 = Flux.fromIterable(strings);
        Flux<String> f2 = Flux.fromIterable(strings);
        Flux<String> sf = Flux.concat(f1, f2);

        StepVerifier.create(sf)
                .expectNextCount(8)
                .verifyComplete();
    }

    private List<String> toList(String s) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return Arrays.asList(s, "demo");
    }
}
