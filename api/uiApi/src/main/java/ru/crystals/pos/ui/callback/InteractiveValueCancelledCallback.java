package ru.crystals.pos.ui.callback;

public class InteractiveValueCancelledCallback<T> extends ValueCallback<T> {

    public enum Action {CHANGED, ENTERED, CANCELLED};

    private final Action action;

    public InteractiveValueCancelledCallback(T value, Action action) {
        super(value);
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

    public Action getAction() {
        return action;
    }
}
