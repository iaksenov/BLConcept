package ru.crystals.pos.bl.api.scenarios;

import ru.crystals.pos.bl.api.listener.VoidListener;
import ru.crystals.pos.ui.UI;

/**
 * Интерфейс сценария с void результатом завершения и отменой
 */
public interface CompleteCancelScenario extends Scenario {

    void start(UI ui, VoidListener onComplete, VoidListener onCancel);

}
