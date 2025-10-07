package org.example.gof.behavioral.observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class StockTicker {
    private final List<Subscriber> subscribers = new ArrayList<>();
    private final Map<String, Double> lastPrices = new HashMap<>();

    public void subscribe(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    public void publish(String symbol, double price) {
        lastPrices.put(symbol, price);
        QuoteUpdate update = new QuoteUpdate(symbol, price);
        subscribers.forEach(subscriber -> subscriber.onNotify(update));
    }

    public Double lastPrice(String symbol) {
        return lastPrices.get(symbol);
    }
}
