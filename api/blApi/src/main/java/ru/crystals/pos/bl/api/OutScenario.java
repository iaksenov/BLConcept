package ru.crystals.pos.bl.api;

import java.util.function.Consumer;

/**
 * Интерфейс сценария с двумя исходами: результат и отмена
 * @param <R> тип результата
 */
public interface OutScenario<R> extends Scenario {

    void start(Consumer<R> onComplete, VoidListener onCancel);

}