//! Пример паттерна "Наблюдатель".

use std::collections::HashMap;

/// Событие для наблюдателей.
#[derive(Debug, Clone, PartialEq)]
pub struct QuoteUpdate {
    pub symbol: String,
    pub price: f64,
}

/// Интерфейс наблюдателя.
pub trait Subscriber {
    fn on_notify(&mut self, update: &QuoteUpdate);
}

/// Издатель, рассылающий обновления котировок.
pub struct StockTicker {
    subscribers: Vec<Box<dyn Subscriber>>, // небольшой пример, поэтому без Send/Sync
    last_prices: HashMap<String, f64>,
}

impl Default for StockTicker {
    fn default() -> Self {
        Self {
            subscribers: Vec::new(),
            last_prices: HashMap::new(),
        }
    }
}

impl StockTicker {
    pub fn subscribe(&mut self, subscriber: Box<dyn Subscriber>) {
        self.subscribers.push(subscriber);
    }

    pub fn publish(&mut self, symbol: impl Into<String>, price: f64) {
        let symbol = symbol.into();
        self.last_prices.insert(symbol.clone(), price);

        let update = QuoteUpdate { symbol, price };
        for subscriber in self.subscribers.iter_mut() {
            subscriber.on_notify(&update);
        }
    }

    pub fn last_price(&self, symbol: &str) -> Option<f64> {
        self.last_prices.get(symbol).copied()
    }
}

/// Простейший наблюдатель, сохраняющий историю.
pub struct HistoryLogger {
    pub log: Vec<QuoteUpdate>,
}

impl HistoryLogger {
    pub fn new() -> Self {
        Self { log: Vec::new() }
    }
}

impl Subscriber for HistoryLogger {
    fn on_notify(&mut self, update: &QuoteUpdate) {
        self.log.push(update.clone());
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    use std::cell::RefCell;
    use std::rc::Rc;

    struct CountingSubscriber {
        hits: Rc<RefCell<usize>>,
    }

    impl CountingSubscriber {
        fn new(hits: Rc<RefCell<usize>>) -> Self {
            Self { hits }
        }
    }

    impl Subscriber for CountingSubscriber {
        fn on_notify(&mut self, _update: &QuoteUpdate) {
            *self.hits.borrow_mut() += 1;
        }
    }

    #[test]
    fn notifies_all_subscribers() {
        let mut ticker = StockTicker::default();
        let hits = Rc::new(RefCell::new(0usize));
        ticker.subscribe(Box::new(CountingSubscriber::new(Rc::clone(&hits))));

        ticker.publish("AAPL", 180.0);
        ticker.publish("AAPL", 182.5);

        assert_eq!(ticker.last_price("AAPL"), Some(182.5));
        assert_eq!(*hits.borrow(), 2);
    }

    #[test]
    fn history_logger_collects_updates() {
        let mut logger = HistoryLogger::new();
        let update = QuoteUpdate { symbol: "NVDA".into(), price: 1000.0 };

        logger.on_notify(&update);

        assert_eq!(logger.log.len(), 1);
        assert_eq!(logger.log[0], update);
    }
}
