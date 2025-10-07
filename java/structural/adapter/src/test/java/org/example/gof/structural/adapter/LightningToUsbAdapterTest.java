package org.example.gof.structural.adapter;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LightningToUsbAdapterTest {

    @Test
    void adapterWrapsLightningPhone() {
        LightningPhone phone = new Iphone();
        UsbCDevice adapter = new LightningToUsbAdapter(phone);

        assertThat(adapter.chargeWithUsbC())
                .contains("Lightning")
                .contains("adapter");
    }
}
