package ru.crystals.pos.screensaver;

import org.springframework.stereotype.Component;
import ru.crystals.pos.bl.api.layer.LayerScenario;
import ru.crystals.pos.ui.UI;
import ru.crystals.pos.ui.UILayer;
import ru.crystals.pos.user.UserModule;

@Component
public class ScreenSaverLayerScenario implements LayerScenario {

    private final UserModule userModule;

    public ScreenSaverLayerScenario(UserModule userModule) {
        this.userModule = userModule;
    }

    @Override
    public UILayer getLayer() {
        return UILayer.SCREEN_SAVER;
    }

    @Override
    public void start(UI ui) {
        userModule.logoff();
    }

}
