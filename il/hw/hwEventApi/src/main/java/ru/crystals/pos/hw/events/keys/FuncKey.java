package ru.crystals.pos.hw.events.keys;

import ru.crystals.pos.hw.events.HWEventPayload;

public class FuncKey  implements HWEventPayload {

    private final FuncKeyType funcKeyType;

    private final String payload;

    public FuncKey(FuncKeyType funcKeyType, String payload) {
        this.funcKeyType = funcKeyType;
        this.payload = payload;
    }

    public FuncKeyType getFuncKeyType() {
        return funcKeyType;
    }

    public String getPayload() {
        return payload;
    }

}
