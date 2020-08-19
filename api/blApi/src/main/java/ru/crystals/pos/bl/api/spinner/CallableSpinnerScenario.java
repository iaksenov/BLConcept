package ru.crystals.pos.bl.api.spinner;

import ru.crystals.pos.bl.api.scenarios.InOutScenario;
import ru.crystals.pos.bl.api.scenarios.special.ScenarioIgnoreAllEvents;

public interface CallableSpinnerScenario<O> extends InOutScenario<CallableSpinnerArg<O>, O>, ScenarioIgnoreAllEvents {

}
