package ru.crystals.pos.bl.common;

import ru.crystals.pos.bl.api.spinner.CallableSpinnerArg;
import ru.crystals.pos.bl.api.spinner.CallableSpinnerScenario;
import ru.crystals.pos.ui.UI;
import ru.crystals.pos.ui.forms.spinner.SpinnerModel;

import java.util.function.Consumer;

public class CallableSpinner<O> implements CallableSpinnerScenario<O> {

    @Override
    public void start(UI ui, CallableSpinnerArg<O> inArg, Consumer<O> onComplete) throws Exception {
        ui.showForm(new SpinnerModel(inArg.getMessage()));
        O result = inArg.getCallable().call();
        onComplete.accept(result);
    }

}
