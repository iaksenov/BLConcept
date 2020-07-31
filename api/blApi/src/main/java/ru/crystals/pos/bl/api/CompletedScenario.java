package ru.crystals.pos.bl.api;

import java.util.function.Consumer;

/**
 * Интерфейс сценария с двумя исходами: результат и отмена
 * @param <Result> тип результата
 */
public interface CompletedScenario<Result> extends Scenario {

    void start(Consumer<Result> onComplete, VoidListener onCancel);

}
