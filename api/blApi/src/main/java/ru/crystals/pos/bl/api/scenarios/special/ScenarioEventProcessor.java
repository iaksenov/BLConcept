package ru.crystals.pos.bl.api.scenarios.special;

import ru.crystals.pos.hw.events.bl.HWBLHumanEvent;

/**
 * Интерфейс для сценариев, позволяющий обрабатывать события и управлять их доставкой родительским сценариям.
 */
public interface ScenarioEventProcessor {

    /**
     * Обработать событие.
     * @param event событие
     * @return true обработано и больше никому не будет отправлено, в.т.ч. и в этот сценарий, <p>
     *         false не обработано и будет отправлено в родительский сценарий
     */
    boolean processEvent(HWBLHumanEvent event);

}
