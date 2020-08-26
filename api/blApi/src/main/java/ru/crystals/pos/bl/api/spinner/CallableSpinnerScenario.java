package ru.crystals.pos.bl.api.spinner;

import ru.crystals.pos.bl.api.scenarios.InOutScenario;
import ru.crystals.pos.bl.api.scenarios.special.ScenarioIgnoreAllEvents;

/**
 * Интерфейс сценария для выполнения долгой операции.
 * На это время блокируется обработка всех событий человека. {@link ru.crystals.pos.hw.events.HumanEvent}
 *
 * @param <O> тип результата
 */
public interface CallableSpinnerScenario<O> extends InOutScenario<CallableSpinnerArg<O>, O>, ScenarioIgnoreAllEvents {

}
