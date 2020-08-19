package ru.crystals.pos.bl.api.scenarios;

import ru.crystals.pos.bl.api.listener.VoidListener;

/**
 * Интерфейс сценария с void результатом завершения
 */
public interface CompleteScenario extends Scenario {

    void start(VoidListener onComplete);

}
