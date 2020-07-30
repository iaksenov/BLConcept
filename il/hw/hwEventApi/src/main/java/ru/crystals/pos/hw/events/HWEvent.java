package ru.crystals.pos.hw.events;

public class HWEvent extends BaseEvent<HWEventPayload> {

    public HWEvent(Object source, HWEventPayload payload) {
        super(source, payload);
    }

}
