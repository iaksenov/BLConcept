package ru.crystals.pos.bl.api.sale;

import ru.crystals.pos.bl.api.layer.LayerScenario;
import ru.crystals.pos.hw.events.listeners.FuncKeyListener;
import ru.crystals.pos.ui.UILayer;

public interface SaleScenario extends FuncKeyListener, LayerScenario {

    @Override
    default UILayer getLayer() {
        return UILayer.SALE;
    }

    void onSubtotal();

}
