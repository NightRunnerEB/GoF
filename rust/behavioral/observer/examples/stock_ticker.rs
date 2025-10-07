use observer::{HistoryLogger, StockTicker, Subscriber, QuoteUpdate};

struct AlertingSubscriber;

impl Subscriber for AlertingSubscriber {
    fn on_notify(&mut self, update: &QuoteUpdate) {
        println!("ALERT: {} is now {:.2}", update.symbol, update.price);
    }
}

fn main() {
    let mut ticker = StockTicker::default();
    let logger = HistoryLogger::new();
    ticker.subscribe(Box::new(AlertingSubscriber));
    ticker.subscribe(Box::new(logger));

    ticker.publish("MSFT", 421.3);
    ticker.publish("GOOGL", 176.5);
}
