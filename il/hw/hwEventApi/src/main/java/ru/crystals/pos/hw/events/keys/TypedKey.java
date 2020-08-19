package ru.crystals.pos.hw.events.keys;

import ru.crystals.pos.hw.events.ui.HWUIHumanEvent;

import java.util.StringJoiner;

/**
 * Событие о нажатии печатаемой клавиши
 */
public class TypedKey implements HWUIHumanEvent {

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
