package ru.crystals.pos.bl.events;

import org.springframework.stereotype.Component;
import ru.crystals.pos.bl.ScenarioManager;
import ru.crystals.pos.bl.api.listener.VoidListener;
import ru.crystals.pos.ui.UILayer;

@Component
public class ShowPopupMessage {

    private final ScenarioManager scenarioManager;

    public ShowPopupMessage(ScenarioManager scenarioManager) {
        this.scenarioManager = scenarioManager;
    }

    public void shopPopup(String msg, VoidListener onComplete) {
        UILayer previousLayer = scenarioManager.getCurrentLayer();
        scenarioManager.setLayer(UILayer.POPUP);
        scenarioManager.start(new ShowMessageScenario(), msg, () -> {
            scenarioManager.setLayer(previousLayer);
            onComplete.call();
        });
    }

}
