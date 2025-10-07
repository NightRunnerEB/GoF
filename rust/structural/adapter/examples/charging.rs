use adapter::{LightningToUsbAdapter, UsbCDevice, Iphone};

fn main() {
    let iphone = Iphone;
    let adapter = LightningToUsbAdapter::new(iphone);

    println!("{}", adapter.charge_with_usb_c());
}
