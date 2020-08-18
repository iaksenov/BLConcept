package ru.crystals.pos.ui.forms;

import ru.crystals.pos.hw.events.interceptor.CallbackInterceptor;

public abstract class UIFormVoidCallbackModel extends UIFormModel {

    private final Runnable callback;

    public UIFormVoidCallbackModel(Runnable callback) {
        this.callback = callback;
    }

    public Runnable getCallback() {
        return () -> CallbackInterceptor.publishCallback(callback);
    }

}
