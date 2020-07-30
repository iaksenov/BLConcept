package ru.crystals.pos.hw.events.keys;

import ru.crystals.pos.hw.events.HWEventPayload;

public class TypedKey implements HWEventPayload {

    private final Character character;

    public TypedKey(Character character) {
        this.character = character;
    }

    public Character getCharacter() {
        return character;
    }

}
