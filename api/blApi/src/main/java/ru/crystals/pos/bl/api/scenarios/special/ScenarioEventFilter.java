package ru.crystals.pos.bl.api.scenarios.special;

import ru.crystals.pos.hw.events.HWHumanEvent;

public interface ScenarioEventFilter {

    /**
     * Фильтровать событие.
     * @param event событие
     * @return true обработано и больше никому не будет отправлено,
     *         false не обработано и будет отправлено в родительский сценарий
     */
    boolean filterEvent(HWHumanEvent event);

}
