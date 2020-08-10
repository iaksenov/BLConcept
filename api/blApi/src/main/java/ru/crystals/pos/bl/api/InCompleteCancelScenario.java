package ru.crystals.pos.bl.api;

import ru.crystals.pos.bl.api.listener.VoidListener;

public interface InCompleteCancelScenario<IN> extends Scenario {

    void start(IN arg, VoidListener onComplete, VoidListener onCancel);

}
