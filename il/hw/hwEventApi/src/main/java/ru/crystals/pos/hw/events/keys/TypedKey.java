package ru.crystals.pos.hw.events.keys;

import ru.crystals.pos.hw.events.HWHumanEvent;

import java.util.StringJoiner;

public class TypedKey implements HWHumanEvent {

    private final Character character;

    public TypedKey(Character character) {
        this.character = character;
    }

    public Character getCharacter() {
        return character;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", TypedKey.class.getSimpleName() + "[", "]")
            .add("character=" + character)
            .toString();
    }
}
