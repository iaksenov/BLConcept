package ru.crystals.pos.bl.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.crystals.pos.bl.LayersManager;
import ru.crystals.pos.bl.api.layer.LayerScenario;
import ru.crystals.pos.ui.UILayer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class LayersManagerImpl implements LayersManager {

    private final ScenarioManagerImpl scenarioManager;
    private final Map<UILayer, LayerScenario> layerScenario = new HashMap<>();

    private Set<LayerScenario> suspended = new HashSet<>();

    public LayersManagerImpl(ScenarioManagerImpl scenarioManager) {
        this.scenarioManager = scenarioManager;
    }

    @Autowired
    private void fillLayerScenarioList(List<LayerScenario> layerScenarioList) {
        for (LayerScenario layersScenario : layerScenarioList) {
            this.layerScenario.put(layersScenario.getLayer(), layersScenario);
        }
    }

    @Override
    public void setLayer(UILayer layer) {
        if (getCurrentLayer() == layer) {
            return;
        }
        if (getCurrentLayer() != null) {
            suspendCurrent();
        }
        LayerScenario layerScenario = this.layerScenario.get(layer);
        setCurrentLayer(layer, layerScenario);
        if (isSuspended(layerScenario)) {
            suspended.remove(layerScenario);
            layerScenario.onResume();
        } else {
            scenarioManager.start(layerScenario);
        }
    }

    private void suspendCurrent() {
        LayerScenario currentScenario = this.layerScenario.get(getCurrentLayer());
        if (currentScenario != null) {
            currentScenario.onSuspend();
            suspended.add(currentScenario);
        }
    }

    private boolean isSuspended(LayerScenario layerScenario) {
        return suspended.contains(layerScenario);
    }

    private UILayer getCurrentLayer() {
        return scenarioManager.getCurrentLayer();
    }

    private void setCurrentLayer(UILayer currentLayer, LayerScenario layerScenario) {
        scenarioManager.setCurrentLayer(currentLayer, layerScenario);
    }
}
