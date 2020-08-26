package ru.crystals.pos.bl.events;

import ru.crystals.pos.bl.api.scenarios.Scenario;

/**
 * Событие, возникающее при смене сценария
 */
public class CurrentScenarioChanged {

    private final Scenario scenario;

    /**
     * Конструктор
     * @param scenario сценарий
     */
    public CurrentScenarioChanged(Scenario scenario) {
        this.scenario = scenario;
    }

    /**
     * Новый текущий сценарий в слое
     * @return сценарий
     */
    public Scenario getCurrentScenario() {
        return scenario;
    }

}
