package ru.crystals.pos.ui.forms;

import ru.crystals.pos.hw.events.interceptor.CallbackInterceptor;

import java.util.function.Consumer;

public abstract class UIFormCallbackModel<C> extends UIFormModel implements UICallbackModel<C> {

    private final Consumer<C> callback;

    public UIFormCallbackModel(Consumer<C> callback) {
        this.callback = callback;
    }

    @Override
    public Consumer<C> getCallback() {
        return c -> CallbackInterceptor.publishCallback(callback, c);
    }

}
