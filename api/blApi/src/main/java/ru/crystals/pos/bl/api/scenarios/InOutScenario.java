package ru.crystals.pos.bl.api.scenarios;

import ru.crystals.pos.ui.UI;

import java.util.function.Consumer;

/**
 * Интерфейс сценария с аргументом и результатом
 * @param <IN> тип аргумента
 * @param <OUT> тип результата
 */
public interface InOutScenario<IN, OUT> extends Scenario {

    void start(UI ui, IN inArg, Consumer<OUT> onComplete) throws Exception;

}
