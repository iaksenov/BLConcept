package ru.crystals.pos.bl;

import ru.crystals.pos.bl.api.Scenario;
import ru.crystals.pos.ui.UI;
import ru.crystals.pos.ui.UIMode;
import ru.crystals.pos.ui.forms.loading.LoginForm;
import ru.crystals.pos.ui.label.Label;

public class LoginScenario implements Scenario<Void> {

    private UI ui;

    public LoginScenario(UI ui) {
        this.ui = ui;
    }

    @Override
    public void start(Void arg) {
        ui.setMode(UIMode.LOGIN);
        ui.showForm(LoginForm.build("Открыта смена №00", Label.empty("Есть незавершенный чек"),
                Label.empty(""), this::onPasswordEntered));
    }

    void onPasswordEntered(String password) {
        // check user password

    }

}
