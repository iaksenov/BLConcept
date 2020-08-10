package ru.crystals.pos.bl.api;

import ru.crystals.pos.bl.api.listener.VoidListener;

import java.util.function.Consumer;

/**
 * Интерфейс сценария без аргумента с результатом и отменой
 * @param <OUT> тип результата
 */
public interface OutCancelScenario<OUT> extends Scenario {

    void start(Consumer<OUT> onComplete, VoidListener onCancel);

}
