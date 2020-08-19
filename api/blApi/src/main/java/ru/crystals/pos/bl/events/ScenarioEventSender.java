package ru.crystals.pos.bl.events;

import ru.crystals.pos.hw.events.bl.HWBLHumanEvent;

public interface ScenarioEventSender {

    void processEvent(HWBLHumanEvent event);

    boolean isIgnoreAllEvents();
}
