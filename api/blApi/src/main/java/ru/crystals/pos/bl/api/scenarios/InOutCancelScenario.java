package ru.crystals.pos.bl.api.scenarios;

import ru.crystals.pos.bl.api.listener.VoidListener;
import ru.crystals.pos.ui.UI;

import java.util.function.Consumer;

/**
 * Интерфейс сценария с аргументом, результатом и отменой
 * @param <IN> тип аргумента
 * @param <OUT> тип результата
 */
public interface InOutCancelScenario<IN, OUT> extends Scenario {

    void start(UI ui, IN inArg, Consumer<OUT> onComplete, VoidListener onCancel);

}
