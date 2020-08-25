package ru.crystals.pos.bl.manager;

import org.springframework.context.ApplicationEventPublisher;
import ru.crystals.pos.bl.api.layer.LayerScenario;
import ru.crystals.pos.bl.api.scenarios.Scenario;
import ru.crystals.pos.bl.events.CurrentScenarioChanged;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ScenariosTree {

    private final List<Scenario> scenarioList = new ArrayList<>();
    private final ApplicationEventPublisher publisher;
    private final LayerScenario layerScenario;

    public ScenariosTree(ApplicationEventPublisher publisher, LayerScenario layerScenario) {
        this.publisher = publisher;
        this.layerScenario = layerScenario;
    }

    public Scenario getLast() {
        return scenarioList.isEmpty() ? layerScenario : scenarioList.get(scenarioList.size() - 1);
    }

    public void addChild(Scenario scenario) {
        scenarioList.add(scenario);
        event(scenario);
        log();
    }

    private void event(Scenario scenario) {
        publisher.publishEvent(new CurrentScenarioChanged(scenario));
    }

    public void replaceLast(Scenario scenario) {
        try {
            if (scenarioList.isEmpty()) {
                scenarioList.add(scenario);
                event(scenario);
            } else {
                scenarioList.set(scenarioList.size() - 1, scenario);
                event(scenario);
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
        event(getLast());
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

    public void log() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        String list = scenarioList.stream().map(s -> s.getClass().getSimpleName()).collect(Collectors.joining(" -> ", "[", "]"));
        return layerScenario.getClass().getSimpleName() + " -> "
            + list;
    }

}
