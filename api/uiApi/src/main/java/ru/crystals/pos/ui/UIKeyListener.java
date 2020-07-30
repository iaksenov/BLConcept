package ru.crystals.pos.ui;

import ru.crystals.pos.hw.events.keys.ControlKey;
import ru.crystals.pos.hw.events.keys.TypedKey;

public interface UIKeyListener {

    void onControlKey(ControlKey controlKey);

    void onTypedKey(TypedKey typedKey);

}
