package ru.crystals.pos.bl.events;

import org.springframework.stereotype.Component;
import ru.crystals.pos.bl.LayersManager;
import ru.crystals.pos.bl.ScenarioManager;
import ru.crystals.pos.bl.api.listener.VoidListener;
import ru.crystals.pos.ui.UILayer;

@Component
public class ShowPopupMessage {

    private final LayersManager layersManager;
    private final ScenarioManager scenarioManager;

    public ShowPopupMessage(LayersManager layersManager, ScenarioManager scenarioManager) {
        this.layersManager = layersManager;
        this.scenarioManager = scenarioManager;
    }

    public void shopPopup(String msg, VoidListener onComplete) {
        UILayer previousLayer = layersManager.getCurrentLayer();
        layersManager.setLayer(UILayer.POPUP);
        scenarioManager.start(new ShowMessageScenario(), msg, () -> {
            layersManager.setLayer(previousLayer);
            onComplete.call();
        });
    }

}
