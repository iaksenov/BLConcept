package ru.crystals.pos.bl.api;

import java.util.function.Consumer;

public interface CompletedScenario<O> extends Scenario {

    void start(Consumer<O> onComplete, VoidListener onCancel);

}
