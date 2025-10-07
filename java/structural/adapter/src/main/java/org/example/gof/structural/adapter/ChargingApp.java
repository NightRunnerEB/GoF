package org.example.gof.structural.adapter;

public final class ChargingApp {
    private ChargingApp() {
    }

    public static void main(String[] args) {
        LightningPhone phone = new Iphone();
        UsbCDevice usbDevice = new LightningToUsbAdapter(phone);

        System.out.println(usbDevice.chargeWithUsbC());
    }
}
