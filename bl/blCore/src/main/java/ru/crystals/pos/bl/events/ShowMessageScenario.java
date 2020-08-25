package ru.crystals.pos.bl.events;

import ru.crystals.pos.bl.api.listener.VoidListener;
import ru.crystals.pos.bl.api.scenarios.InCompleteScenario;
import ru.crystals.pos.ui.UI;
import ru.crystals.pos.ui.forms.message.MessageFormModel;

public class ShowMessageScenario implements InCompleteScenario<String> {

    @Override
    public void start(UI ui, String msg, VoidListener onComplete) {
        ui.showForm(new MessageFormModel(msg, onComplete::call));
    }

}
