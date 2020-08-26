package ru.crystals.pos.bl.events;

import org.springframework.stereotype.Component;
import ru.crystals.pos.bl.ScenarioManager;
import ru.crystals.pos.hw.events.HWHumanEvent;
import ru.crystals.pos.hw.events.listeners.Barcode;
import ru.crystals.pos.ui.UILayer;
import ru.crystals.pos.user.UserModule;

@Component
public class EventPreProcessor {

    private final UserModule userModule;
    private final ScenarioManager scenarioManager;

    public EventPreProcessor(UserModule userModule, ScenarioManager scenarioManager) {
        this.userModule = userModule;
        this.scenarioManager = scenarioManager;
    }

    private boolean preProcessBarcode(String barcode) {
        if (userModule.isBarcodeForLogin(barcode)) {
            if (scenarioManager.getCurrentLayer() != UILayer.LOGIN) {
                scenarioManager.setLayer(UILayer.LOGIN);
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
        if (event instanceof Barcode) {
            return preProcessBarcode(((Barcode) event).getCode());
        } else {
            return false;
        }
    }
}
