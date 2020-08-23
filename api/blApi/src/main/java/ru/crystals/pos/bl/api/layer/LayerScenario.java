package ru.crystals.pos.bl.api.layer;

import ru.crystals.pos.bl.api.scenarios.Scenario;
import ru.crystals.pos.ui.UI;
import ru.crystals.pos.ui.UILayer;

public interface LayerScenario extends Scenario {

    /**
     * Слой сценария
     * @return слой
     */
    UILayer getLayer();

    /**
     * Запуск сценария
     */
    void start(UI ui);

    /**
     * при приостановлении
     */
    default void onSuspend() {};

    /**
     * при возобновлении
     */
    default void onResume() {};

}
