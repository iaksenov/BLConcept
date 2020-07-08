package ru.crystals.pos.ui;

import ru.crystals.pos.hw.events.listeners.key.ControlKey;
import ru.crystals.pos.hw.events.listeners.key.TypedKey;
import ru.crystals.pos.ui.forms.UIForm;

public interface UI {

    void setLocale(Locale locale);

    void setMode(UIMode uiMode);

    <U extends UIForm> U showForm(U uiForm);

    void dispatchControlKey(ControlKey controlKey);

    void dispatchTypedKey(TypedKey controlKey);

}