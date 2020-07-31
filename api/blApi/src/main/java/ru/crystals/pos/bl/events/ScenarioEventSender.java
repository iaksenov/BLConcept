package ru.crystals.pos.bl.events;

import ru.crystals.pos.hw.events.keys.FuncKey;
import ru.crystals.pos.hw.events.listeners.MSRTracks;

public interface ScenarioEventSender {

    void onBarcode(String code);

    void onFunctionalKey(FuncKey funcKey);

    void onMSR(MSRTracks msrTracks);
}
