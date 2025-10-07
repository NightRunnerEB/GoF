package org.example.gof.behavioral.observer;

import java.util.ArrayList;
import java.util.List;

public final class HistoryLogger implements Subscriber {
    private final List<QuoteUpdate> history = new ArrayList<>();

    @Override
    public void onNotify(QuoteUpdate update) {
        history.add(update);
    }

    public List<QuoteUpdate> history() {
        return List.copyOf(history);
    }
}
