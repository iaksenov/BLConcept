package ru.crystals.pos.bl.api.spinner;

import ru.crystals.pos.bl.api.marker.IgnoreAllEvents;
import ru.crystals.pos.bl.api.scenarios.InOutScenario;

public interface CallableSpinnerScenario<O> extends InOutScenario<CallableSpinnerArg<O>, O>, IgnoreAllEvents {

}
