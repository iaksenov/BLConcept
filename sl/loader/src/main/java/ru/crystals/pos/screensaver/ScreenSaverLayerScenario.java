package ru.crystals.pos.screensaver;

import org.springframework.stereotype.Component;
import ru.crystals.pos.bl.api.layer.LayerScenario;
import ru.crystals.pos.ui.UI;
import ru.crystals.pos.ui.UILayer;

@Component
public class ScreenSaverLayerScenario implements LayerScenario {

    @Override
    public UILayer getLayer() {
        return UILayer.SCREEN_SAVER;
    }

    @Override
    public void start(UI ui) {
    }

}
