package ru.crystals.pos.hw.events.ui;

import ru.crystals.pos.hw.events.HumanEvent;

import java.util.function.Consumer;

public class UIHumanEvent<V> implements HumanEvent {

    private final Consumer<V> consumer;
    private final V value;
    private final Runnable runnable;

    public UIHumanEvent(Runnable runnable) {
        this.consumer = null;
        this.value = null;
        this.runnable = runnable;
    }

    public UIHumanEvent(Consumer<V> consumer, V value) {
        this.consumer = consumer;
        this.value = value;
        this.runnable = null;
    }

    public void accept() {
        if (consumer != null) {
            consumer.accept(value);
        } else if (runnable != null) {
            runnable.run();
        }
    }

}
