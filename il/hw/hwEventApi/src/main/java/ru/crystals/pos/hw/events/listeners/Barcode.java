package ru.crystals.pos.hw.events.listeners;

import ru.crystals.pos.hw.events.HWEventPayload;

public class Barcode implements HWEventPayload {

    private final String code;

    public Barcode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
