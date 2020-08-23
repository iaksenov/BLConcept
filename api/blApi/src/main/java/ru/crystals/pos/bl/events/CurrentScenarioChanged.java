package ru.crystals.pos.bl.events;

import ru.crystals.pos.bl.api.scenarios.Scenario;

public class CurrentScenarioChanged {

    private final Scenario scenario;

    public CurrentScenarioChanged(Scenario scenario) {
        this.scenario = scenario;
    }

    public Scenario getCurrentScenario() {
        return scenario;
    }

}
