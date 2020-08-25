package ru.crystals.pos.bl.api.scenarios;

import ru.crystals.pos.bl.api.listener.VoidListener;
import ru.crystals.pos.ui.UI;

/**
 * Интерфейс сценария с аргументом и void результатом
 * @param <IN> тип аргумента
 */
public interface InCompleteScenario<IN> extends Scenario {

    void start(UI ui, IN arg, VoidListener onComplete);

}
