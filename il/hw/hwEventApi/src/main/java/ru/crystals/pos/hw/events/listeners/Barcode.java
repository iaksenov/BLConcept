package ru.crystals.pos.hw.events.listeners;

import ru.crystals.pos.hw.events.HWHumanEvent;

import java.util.StringJoiner;

public class Barcode implements HWHumanEvent {

    private final String code;

    public Barcode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Barcode.class.getSimpleName() + "[", "]")
            .add("code='" + code + "'")
            .toString();
    }
}
