package ru.crystals.pos.hw.events.keys;

import ru.crystals.pos.hw.events.ui.HWUIHumanEvent;

import java.util.StringJoiner;

/**
 * Событие о нажатии клавиши управления
 */
public class ControlKey implements HWUIHumanEvent {

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
