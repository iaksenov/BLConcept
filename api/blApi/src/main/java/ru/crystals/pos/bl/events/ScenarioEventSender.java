package ru.crystals.pos.bl.events;

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
     * @see ru.crystals.pos.bl.api.scenarios.special.ScenarioIgnoreAllEvents
     * @return true - игнорировать, false - не игнорировать
     */
    boolean isIgnoreAllEvents();
}
