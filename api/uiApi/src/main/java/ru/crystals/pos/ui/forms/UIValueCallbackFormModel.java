package ru.crystals.pos.ui.forms;

import ru.crystals.pos.hw.events.interceptor.CallbackInterceptor;
import ru.crystals.pos.ui.callback.ValueCallback;

import java.util.function.Consumer;

/**
 * Модель формы с колбэком и возможностью считать значение
 * @param <V> тип значения
 * @param <C> тип колбэка значения
 */
public abstract class UIValueCallbackFormModel<V, C extends ValueCallback<V>> extends UIValueFormModel<V> implements UICallbackModel<C> {

    private final Consumer<C> consumer;

    public UIValueCallbackFormModel(Consumer<C> consumer) {
        this.consumer = consumer;
    }

    @Override
    public Consumer<C> getCallback() {
        return v -> CallbackInterceptor.publishCallback(consumer, v);
    }

}
