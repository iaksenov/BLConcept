package ru.crystals.pos.ui.forms;

import java.util.function.Consumer;

public class UIFormCallbackModel<C> extends UIFormModel {

    private Consumer<C> callback;

    public UIFormCallbackModel(Consumer<C> callback) {
        this.callback = callback;
    }

    public Consumer<C> getCallback() {
        return callback;
    }

}
