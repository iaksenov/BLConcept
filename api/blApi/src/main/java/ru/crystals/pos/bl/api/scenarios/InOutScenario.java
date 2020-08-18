package ru.crystals.pos.bl.api.scenarios;

import java.util.function.Consumer;

/**
 * Интерфейс сценария с аргументом и результатом
 * @param <IN> тип аргумента
 * @param <OUT> тип результата
 */
public interface InOutScenario<IN, OUT> extends Scenario {

    void start(IN inArg, Consumer<OUT> onComplete) throws Exception;

}
