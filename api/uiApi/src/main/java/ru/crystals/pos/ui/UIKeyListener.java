package ru.crystals.pos.ui;

import ru.crystals.pos.hw.events.keys.ControlKey;
import ru.crystals.pos.hw.events.keys.TypedKey;

/**
 * Интерфейс для реализации GUI в части доставки клавиатурных событий
 */
public interface UIKeyListener {

    /**
     * Клавиша управления
     *
     * @param controlKey клавиша
     */
    void onControlKey(ControlKey controlKey);

    /**
     * Печатаемая клавиша
     *
     * @param typedKey клавиша
     */
    void onTypedKey(TypedKey typedKey);

}
