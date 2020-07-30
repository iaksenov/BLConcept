package ru.crystals.pos.ui.forms.loading;

import ru.crystals.pos.ui.forms.UIFormModel;
import ru.crystals.pos.ui.label.Label;

import java.util.function.Consumer;

public class LoginFormModel extends UIFormModel {

    private final String title;

    private final Label infoLabel;

    private final Label loginFailedText;

    private final Consumer<String> passwordCallback;

    public LoginFormModel(String title, Label infoLabel, Label loginFailedText, Consumer<String> passwordCallback) {
        this.title = title;
        this.infoLabel = infoLabel;
        this.loginFailedText = loginFailedText;
        this.passwordCallback = passwordCallback;
    }

    public String getTitle() {
        return title;
    }

    public Label getInfoLabel() {
        return infoLabel;
    }

    public Label getLoginFailedText() {
        return loginFailedText;
    }

    public Consumer<String> getPasswordCallback() {
        return passwordCallback;
    }
}
