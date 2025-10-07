package org.example.gof.behavioral.observer;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

class StockTickerTest {

    @Test
    void distributesUpdatesToSubscribers() {
        StockTicker ticker = new StockTicker();
        AtomicInteger counter = new AtomicInteger();

        ticker.subscribe(update -> counter.incrementAndGet());
        ticker.publish("AAPL", 180.0);
        ticker.publish("AAPL", 182.5);

        assertThat(ticker.lastPrice("AAPL")).isEqualTo(182.5);
        assertThat(counter).hasValue(2);
    }

    @Test
    void historyLoggerKeepsImmutableCopy() {
        HistoryLogger logger = new HistoryLogger();
        QuoteUpdate update = new QuoteUpdate("NVDA", 1000.0);

        logger.onNotify(update);

        assertThat(logger.history())
                .hasSize(1)
                .containsExactly(update);
    }
}
