package ru.crystals.pos.hw.events;

import java.util.function.Consumer;

public class UIHumanEvent<O> implements HumanEvent {

    private final Consumer<O> consumer;
    private final O object;
    private final Runnable runnable;

    public UIHumanEvent(Runnable runnable) {
        this.consumer = null;
        this.object = null;
        this.runnable = runnable;
    }

    public UIHumanEvent(Consumer<O> c, O o) {
        this.consumer = c;
        this.object = o;
        this.runnable = null;
    }

    public void accept() {
        if (consumer != null) {
            consumer.accept(object);
        } else if (runnable != null) {
            runnable.run();
        }
    }

}
