package ru.crystals.pos.bl.events;

import org.springframework.stereotype.Component;
import ru.crystals.pos.bl.LayersManager;
import ru.crystals.pos.bl.ScenarioManager;
import ru.crystals.pos.bl.api.login.LoginScenario;
import ru.crystals.pos.hw.events.HWHumanEvent;
import ru.crystals.pos.hw.events.listeners.Barcode;
import ru.crystals.pos.ui.UILayer;
import ru.crystals.pos.user.UserModule;

@Component
public class EventPreProcessor {

    private final UserModule userModule;
    private final ScenarioManager scenarioManager;
    private LayersManager layersManager;

    public EventPreProcessor(UserModule userModule, ScenarioManager scenarioManager, LayersManager layersManager) {
        this.userModule = userModule;
        this.scenarioManager = scenarioManager;
        this.layersManager = layersManager;
    }

    private boolean preProcessBarcode(String barcode) {
        if (userModule.isBarcodeForLogin(barcode)) {
            if (scenarioManager.getCurrentScenario() instanceof LoginScenario) {
                return false;
            } else {
                layersManager.setLayer(UILayer.LOGIN);
                if (userModule.isCurrentUserBarcode(barcode)) {
                    userModule.logoff();
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    public boolean processEvent(HWHumanEvent event) {
        if (layersManager.getCurrentLayer() == UILayer.SCREEN_SAVER) {
            layersManager.setLayer(UILayer.LOGIN);
        }
        if (event instanceof Barcode) {
            return preProcessBarcode(((Barcode) event).getCode());
        } else {
            return false;
        }
    }
}
