// --- Подсистема (сложные детали, могли бы быть в разных модулях) ---
struct Inventory;
impl Inventory {
    fn reserve(&self, sku: &str, qty: u32) -> bool {
        println!("Inventory: reserving {} x {}", qty, sku);
        true
    }
}

struct Payment;
impl Payment {
    fn charge(&self, customer_id: &str, amount_cents: u64) -> Result<(), String> {
        println!("Payment: charge {} for {} cents", customer_id, amount_cents);
        Ok(())
    }
}

struct Shipping;
impl Shipping {
    fn create_label(&self, sku: &str, qty: u32, address: &str) -> String {
        println!("Shipping: label for {} x {} to {}", qty, sku, address);
        format!("LBL-{}-{}", sku, qty)
    }
}

struct Notifier;
impl Notifier {
    fn email(&self, to: &str, subject: &str, body: &str) {
        println!("Email -> {to}: {subject}\n{body}");
    }
}

// --- Фасад: один простой вход для клиента ---
pub struct OrderFacade {
    inv: Inventory,
    pay: Payment,
    ship: Shipping,
    mail: Notifier,
}

impl OrderFacade {
    pub fn new() -> Self {
        Self {
            inv: Inventory,
            pay: Payment,
            ship: Shipping,
            mail: Notifier,
        }
    }

    pub fn place_order(
        &self,
        customer_id: &str,
        email: &str,
        shipping_address: &str,
        sku: &str,
        qty: u32,
        price_cents: u64,
    ) -> Result<String, String> {
        // фиксируем «правильную» последовательность шагов
        if !self.inv.reserve(sku, qty) {
            return Err("Out of stock".into());
        }

        self.pay.charge(customer_id, price_cents * qty as u64)?;

        let label = self.ship.create_label(sku, qty, shipping_address);

        self.mail.email(
            email,
            "Order confirmed",
            &format!("Your order {sku} x {qty} is on the way. Label: {label}"),
        );

        Ok(label)
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn example() {
        let facade = OrderFacade::new();
        let label = facade.place_order(
            "cust_42",
            "user@example.com",
            "Baker St. 221B",
            "SKU-CPU-7950X",
            1,
            499_00,
        )?;
        println!("Done. Shipping label: {label}");
    }
}
