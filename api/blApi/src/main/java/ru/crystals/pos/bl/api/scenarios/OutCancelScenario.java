package ru.crystals.pos.bl.api.scenarios;

import ru.crystals.pos.bl.api.listener.VoidListener;
import ru.crystals.pos.ui.UI;

import java.util.function.Consumer;

/**
 * Интерфейс сценария без аргумента с результатом и отменой
 * @param <OUT> тип результата
 */
public interface OutCancelScenario<OUT> extends Scenario {

    void start(UI ui, Consumer<OUT> onComplete, VoidListener onCancel);

}
