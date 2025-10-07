package org.example.gof.behavioral.observer;

public interface Subscriber {
    void onNotify(QuoteUpdate update);
}
