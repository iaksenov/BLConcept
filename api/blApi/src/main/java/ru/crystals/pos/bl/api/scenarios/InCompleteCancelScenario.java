package ru.crystals.pos.bl.api.scenarios;

import ru.crystals.pos.bl.api.listener.VoidListener;

/**
 * Интерфейс сценария с аргументом, void результатом и отменой
 * @param <IN> тип аргумента
 */
public interface InCompleteCancelScenario<IN> extends Scenario {

    void start(IN arg, VoidListener onComplete, VoidListener onCancel);

}
