//! Реализация паттерна "Адаптер".

/// Клиентский интерфейс, ожидающий работу по USB-C.
pub trait UsbCDevice {
    fn charge_with_usb_c(&self) -> String;
}

/// Существующий (несовместимый) интерфейс Lightning.
pub trait LightningPhone {
    fn charge_with_lightning(&self) -> String;
}

/// Конкретный класс, поддерживающий только Lightning.
pub struct Iphone;

impl LightningPhone for Iphone {
    fn charge_with_lightning(&self) -> String {
        "Charging iPhone via Lightning".to_string()
    }
}

/// Адаптер, обеспечивающий совместимость Lightning -> USB-C.
pub struct LightningToUsbAdapter<T: LightningPhone> {
    phone: T,
}

impl<T: LightningPhone> LightningToUsbAdapter<T> {
    pub fn new(phone: T) -> Self {
        Self { phone }
    }
}

impl<T: LightningPhone> UsbCDevice for LightningToUsbAdapter<T> {
    fn charge_with_usb_c(&self) -> String {
        format!("{} (adapter)", self.phone.charge_with_lightning())
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn adapter_wraps_lightning_device() {
        let iphone = Iphone;
        let adapter = LightningToUsbAdapter::new(iphone);
        let message = adapter.charge_with_usb_c();

        assert!(message.contains("Lightning"));
        assert!(message.contains("adapter"));
    }
}
