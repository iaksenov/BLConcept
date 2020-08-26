package ru.crystals.pos.bl.api.layer;

import ru.crystals.pos.bl.api.scenarios.Scenario;
import ru.crystals.pos.ui.UI;
import ru.crystals.pos.ui.UILayer;

/**
 * Интерфейс сценария соответствующего слоя. <p>
 * Реализаций должно быть не больше перечисления {@link UILayer}
 */
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
     * Вызывается менеджером сценариев при приостановлении.
     * {@link ru.crystals.pos.bl.ScenarioManager#setLayer(UILayer)}
     */
    default void onSuspend() {};

    /**
     * Вызывается менеджером сценариев при возобновлении, если ранее был приостанов.
     * {@link ru.crystals.pos.bl.ScenarioManager#setLayer(UILayer)}
     */
    default void onResume() {};

}
