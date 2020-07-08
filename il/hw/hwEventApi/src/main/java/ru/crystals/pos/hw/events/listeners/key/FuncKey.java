package ru.crystals.pos.hw.events.listeners.key;

public class FuncKey {

    private FuncKeyType funcKeyType;

    private String payload;

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
