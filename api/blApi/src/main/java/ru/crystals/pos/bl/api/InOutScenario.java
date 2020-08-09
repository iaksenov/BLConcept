package ru.crystals.pos.bl.api;

import java.util.function.Consumer;

public interface InOutScenario<IN, OUT> extends Scenario {

    void start(IN inArg, Consumer<OUT> onComplete, VoidListener onCancel);

}
