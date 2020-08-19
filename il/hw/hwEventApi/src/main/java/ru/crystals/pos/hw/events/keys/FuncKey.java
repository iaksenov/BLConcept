package ru.crystals.pos.hw.events.keys;

import ru.crystals.pos.hw.events.bl.HWBLHumanEvent;

import java.util.StringJoiner;

/**
 * Событие о нажатии функциональной клавиши
 */
public class FuncKey  implements HWBLHumanEvent {

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

    @Override
    public String toString() {
        return new StringJoiner(", ", FuncKey.class.getSimpleName() + "[", "]")
            .add("funcKeyType=" + funcKeyType)
            .add("payload='" + payload + "'")
            .toString();
    }
}
