package ca.psdev.mssc.productevents.sandbox;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.util.function.Tuples;

import java.time.Duration;

@Slf4j
public class InfiniteStreamsTest {

    @Test
    public void shouldCreateInfiniteSequence() {
        Flux<Long> seq = Flux.interval(Duration.ofMillis(100))
                .log()
                .delayElements(Duration.ofSeconds(1))
                .map(l ->  l + 1l)
                .take(3)
                ;

        StepVerifier.create(seq)
            .expectSubscription()
            .expectNext(1l, 2l, 3l)
            .thenCancel()
            .verify();
    }

    @Test
    public void shouldCreateFibonacci() {
        Flux<Long> fib = Flux.generate(
                () -> Tuples.of(0l, 1l),
                (state, accumulator) -> {
                    accumulator.next(state.getT1());
                    return Tuples.of(state.getT2(), state.getT1() + state.getT2());
                }
        );

        Flux<Long> fibonacciFlux = fib.take(6).log();

        StepVerifier.create(fibonacciFlux)
                .expectNext(0l, 1l, 1l, 2l, 3l, 5l)
                .expectComplete()
                .verify();
    }

    @Test
    public void shouldHandleBackpressure() {
        Flux<Integer> f = Flux.range(1, 100).log();

        f.subscribe(new BaseSubscriber<Integer>() {
            @Override
            protected void hookOnNext(Integer value) {
                request(1);
                log.info("Obtained " + value);
                if (value >= 10) {
                    cancel();
                }
            }
        });
    }

}
