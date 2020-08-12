package ru.crystals.pos.ui.callback;

public class InteractiveValueCancelledCallback<T> {

    public enum Action {CHANGED, ENTERED, CANCELLED};

    private final T value;

    private final Action action;

    public InteractiveValueCancelledCallback(T value, Action action) {
        this.value = value;
        this.action = action;
    }

    public static <T> InteractiveValueCancelledCallback<T> entered(T value) {
        return new InteractiveValueCancelledCallback<>(value, Action.ENTERED);
    }

    public static <T> InteractiveValueCancelledCallback<T> changed(T value) {
        return new InteractiveValueCancelledCallback<>(value, Action.CHANGED);
    }

    public static <T> InteractiveValueCancelledCallback<T> cancelled() {
        return new InteractiveValueCancelledCallback<>(null, Action.CANCELLED);
    }

    public T getValue() {
        return value;
    }

    public Action getAction() {
        return action;
    }
}
