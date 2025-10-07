package org.example.gof.structural.adapter;

public final class LightningToUsbAdapter implements UsbCDevice {
    private final LightningPhone phone;

    public LightningToUsbAdapter(LightningPhone phone) {
        this.phone = phone;
    }

    @Override
    public String chargeWithUsbC() {
        return phone.chargeWithLightning() + " (adapter)";
    }
}
