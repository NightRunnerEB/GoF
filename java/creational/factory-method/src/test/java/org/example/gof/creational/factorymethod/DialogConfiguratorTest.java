package org.example.gof.creational.factorymethod;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DialogConfiguratorTest {

    @Test
    void desktopEnvironmentCreatesWindowsDialog() {
        Dialog dialog = DialogConfigurator.forEnvironment("desktop");
        assertThat(dialog.render()).contains("Windows button");
    }

    @Test
    void webEnvironmentCreatesHtmlDialog() {
        Dialog dialog = DialogConfigurator.forEnvironment("web");
        assertThat(dialog.render()).contains("HTML button");
    }
}
