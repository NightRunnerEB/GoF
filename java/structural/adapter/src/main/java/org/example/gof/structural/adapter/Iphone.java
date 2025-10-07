package org.example.gof.structural.adapter;

public final class Iphone implements LightningPhone {
    @Override
    public String chargeWithLightning() {
        return "Charging iPhone via Lightning";
    }
}
