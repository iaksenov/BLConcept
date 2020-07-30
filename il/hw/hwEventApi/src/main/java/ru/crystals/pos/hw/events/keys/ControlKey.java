package ru.crystals.pos.hw.events.keys;

import ru.crystals.pos.hw.events.HWEventPayload;

public class ControlKey implements HWEventPayload {

    private final ControlKeyType controlKeyType;

    public ControlKey(ControlKeyType controlKeyType) {
        this.controlKeyType = controlKeyType;
    }

    public ControlKeyType getControlKeyType() {
        return controlKeyType;
    }
}
