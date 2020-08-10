package ru.crystals.pos.bl.api.login;

import ru.crystals.pos.bl.api.layer.LayerScenario;
import ru.crystals.pos.hw.events.listeners.BarcodeListener;
import ru.crystals.pos.hw.events.listeners.MSRListener;
import ru.crystals.pos.ui.UILayer;

public interface LoginScenario extends BarcodeListener, MSRListener, LayerScenario {

    @Override
    default UILayer getLayer() {
        return UILayer.LOGIN;
    }

}
