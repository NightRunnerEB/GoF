/// middleware-style

trait Handler {
    fn set_next(&mut self, next: Option<Box<dyn Handler>>);
    fn handle(&self, req: &str);
}

struct BaseHandler {
    name: &'static str,
    next: Option<Box<dyn Handler>>,
}

impl BaseHandler {
    fn new(name: &'static str) -> Self {
        Self { name, next: None }
    }
}

impl Handler for BaseHandler {
    fn set_next(&mut self, next: Option<Box<dyn Handler>>) {
        self.next = next;
    }

    fn handle(&self, req: &str) {
        println!("{} received: {}", self.name, req);

        if let Some(ref next) = self.next {
            next.handle(req);
        }
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn example() {
        let mut auth = BaseHandler::new("Auth");
        let mut log = BaseHandler::new("Logger");
        let biz = BaseHandler::new("Business");

        log.set_next(Some(Box::new(biz)));
        auth.set_next(Some(Box::new(log)));

        auth.handle("auth request");
        auth.handle("normal request");
    }
}
