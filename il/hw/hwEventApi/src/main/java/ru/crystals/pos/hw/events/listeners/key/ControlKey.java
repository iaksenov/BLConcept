package ru.crystals.pos.hw.events.listeners.key;

public class ControlKey {

    private ControlKeyType controlKeyType;

    public ControlKey(ControlKeyType controlKeyType) {
        this.controlKeyType = controlKeyType;
    }

    public ControlKeyType getControlKeyType() {
        return controlKeyType;
    }
}
