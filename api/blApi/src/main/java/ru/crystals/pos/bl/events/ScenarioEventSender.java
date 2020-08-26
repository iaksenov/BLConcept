package ru.crystals.pos.bl.events;

import ru.crystals.pos.bl.api.scenarios.special.IgnoreAllEvents;
import ru.crystals.pos.hw.events.bl.HWBLHumanEvent;

/**
 * Интерфейс реализации отправки части человеческих событий в слой БЛ (активным сценариям)
 */
public interface ScenarioEventSender {

    /**
     * Обработать событие
     * @param event событие
     */
    void processEvent(HWBLHumanEvent event);

    /**
     * Надо ли игнорировать все события
     * @see IgnoreAllEvents
     * @return true - игнорировать, false - не игнорировать
     */
    boolean isIgnoreAllEvents();
}
