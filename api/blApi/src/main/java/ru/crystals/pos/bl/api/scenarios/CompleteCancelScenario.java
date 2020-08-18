package ru.crystals.pos.bl.api.scenarios;

import ru.crystals.pos.bl.api.listener.VoidListener;

public interface CompleteCancelScenario extends Scenario {

    void start(VoidListener onComplete, VoidListener onCancel);

}
