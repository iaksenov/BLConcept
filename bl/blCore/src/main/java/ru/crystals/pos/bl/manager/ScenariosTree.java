package ru.crystals.pos.bl.manager;

import ru.crystals.pos.bl.api.Scenario;
import ru.crystals.pos.bl.api.layer.LayerScenario;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class ScenariosTree {

    private final List<Scenario> scenarioList = new ArrayList<>();
    private final LayerScenario layerScenario;

    public ScenariosTree(LayerScenario layerScenario) {
        this.layerScenario = layerScenario;
    }

    public Scenario getLast() {
        return scenarioList.isEmpty() ? layerScenario : scenarioList.get(scenarioList.size() - 1);
    }

    public void addChild(Scenario scenario) {
        scenarioList.add(scenario);
        log();
    }

    public void replaceLast(Scenario scenario) {
        try {
            if (scenarioList.isEmpty()) {
                scenarioList.add(scenario);
            } else {
                scenarioList.set(scenarioList.size() - 1, scenario);
            }
        } finally {
            log();
        }
    }

    public void remove(Scenario scenario) {
        int i = scenarioList.indexOf(scenario);
        if (i >= 0) {
            while (scenarioList.size() > i) {
                scenarioList.remove(scenarioList.size() - 1);
            }
        }
        log();
    }

    public Scenario getParent(Scenario scenario) {
        if (scenario instanceof LayerScenario) {
            return null;
        }
        int i = scenarioList.indexOf(scenario);
        return i > 0 ? scenarioList.get(i-1) : layerScenario;
    }

    public Scenario getChild(Scenario scenario) {
        if (scenario == layerScenario) {
            return scenarioList.isEmpty() ? null : scenarioList.get(0);
        } else {
            int i = scenarioList.indexOf(scenario);
            return (i >= 0 && i < (scenarioList.size() - 1)) ? scenarioList.get(i + 1) : null;
        }
    }

    public boolean contains(Scenario scenario) {
        return scenario == layerScenario || scenarioList.contains(scenario);
    }

    private void log() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        return layerScenario.getClass().getSimpleName() + " -> "
            + new StringJoiner(" -> ", "[", "]")
            .add("scenarioList=" + scenarioList)
            .toString();
    }

}
