package ru.crystals.pos.ui.forms.loading;

import ru.crystals.pos.ui.forms.UIFormModel;
import ru.crystals.pos.ui.label.Label;

import java.util.function.Consumer;

public class LoginFormModel extends UIFormModel {

    /**
     * Заголовок
     */
    private final String title;

    /**
     * Информация о состоянии
     */
    private final Label infoLabel;

    /**
     * Причина ошибки авторизации
     */
    private Label loginFailedText;

    /**
     * Callback ввода пароля
     */
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

    public void setLoginFailedText(Label loginFailedText) {
        this.loginFailedText = loginFailedText;
    }

    public Consumer<String> getPasswordCallback() {
        return passwordCallback;
    }
}
