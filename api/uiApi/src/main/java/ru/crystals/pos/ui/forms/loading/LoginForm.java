package ru.crystals.pos.ui.forms.loading;

import ru.crystals.pos.ui.forms.UIForm;
import ru.crystals.pos.ui.label.Label;

import java.util.function.Consumer;

public class LoginForm implements UIForm {

    private final String title;

    private final Label infoLabel;

    private final Label loginFailedText;

    private final Consumer<String> passwordCallback;

    public LoginForm(String title, Label infoLabel, Label loginFailedText, Consumer<String> passwordCallback) {
        this.title = title;
        this.infoLabel = infoLabel;
        this.loginFailedText = loginFailedText;
        this.passwordCallback = passwordCallback;
    }

}
