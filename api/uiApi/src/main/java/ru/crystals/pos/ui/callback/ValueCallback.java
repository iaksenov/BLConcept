package ru.crystals.pos.ui.callback;

public class ValueCallback<T> {

    private final T value;

    public ValueCallback(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }
}
