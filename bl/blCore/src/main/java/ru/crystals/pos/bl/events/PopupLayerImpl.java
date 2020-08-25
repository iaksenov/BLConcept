package ru.crystals.pos.bl.events;

import org.springframework.stereotype.Component;
import ru.crystals.pos.bl.api.layer.LayerScenario;
import ru.crystals.pos.ui.UI;
import ru.crystals.pos.ui.UILayer;

@Component
public class PopupLayerImpl implements LayerScenario {

    @Override
    public UILayer getLayer() {
        return UILayer.POPUP;
    }

    @Override
    public void start(UI ui) {

    }

}
