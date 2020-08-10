package ru.crystals.pos.bl.api.layer;

import ru.crystals.pos.bl.api.Scenario;
import ru.crystals.pos.ui.UILayer;

public interface LayerScenario extends Scenario {

    /**
     * Слой сцнария
     * @return слой
     */
    UILayer getLayer();

    /**
     * Запуск сценария
     */
    void start();

    /**
     * при приостановлении
     */
    default void onSuspend() {};

    /**
     * при возобновлении
     */
    void onResume();

}