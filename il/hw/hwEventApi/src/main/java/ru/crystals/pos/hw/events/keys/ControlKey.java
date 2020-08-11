package ru.crystals.pos.hw.events.keys;

import ru.crystals.pos.hw.events.HWHumanEvent;

import java.util.StringJoiner;

public class ControlKey implements HWHumanEvent {

    private final ControlKeyType controlKeyType;

    public ControlKey(ControlKeyType controlKeyType) {
        this.controlKeyType = controlKeyType;
    }

    public ControlKeyType getControlKeyType() {
        return controlKeyType;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ControlKey.class.getSimpleName() + "[", "]")
            .add("controlKeyType=" + controlKeyType)
            .toString();
    }
}
