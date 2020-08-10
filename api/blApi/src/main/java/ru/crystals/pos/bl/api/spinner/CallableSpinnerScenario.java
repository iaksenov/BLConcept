package ru.crystals.pos.bl.api.spinner;

import ru.crystals.pos.bl.api.InOutScenario;
import ru.crystals.pos.bl.api.marker.IgnoreAllEvents;

public interface CallableSpinnerScenario<O> extends InOutScenario<CallableSpinnerArg<O>, O>, IgnoreAllEvents {

}
