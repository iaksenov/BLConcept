package ru.crystals.pos.bl.events;

import ru.crystals.pos.hw.events.HWHumanEvent;

public interface ScenarioEventSender {

    void processEvent(HWHumanEvent event);

    boolean isIgnoreCurrentEvents();
}
