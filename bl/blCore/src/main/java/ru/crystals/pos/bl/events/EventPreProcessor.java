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
        if (!(scenarioManager.getCurrentScenario() instanceof LoginScenario) && userModule.isCurrentUserBarcode(barcode)) {
            layersManager.setLayer(UILayer.LOGIN);
            return true;
        }
        return false;
    }

    public boolean processEvent(HWHumanEvent event) {
        if (event instanceof Barcode) {
            return preProcessBarcode(((Barcode) event).getCode());
        } else {
            return false;
        }
    }
}
