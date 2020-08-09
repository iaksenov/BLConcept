package ru.crystals.pos.ui.forms.loading;

import ru.crystals.pos.ui.forms.UIFormCallbackModel;
import ru.crystals.pos.ui.label.Label;

import java.util.function.Consumer;

public class LoginFormModel extends UIFormCallbackModel<String> {

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

    public LoginFormModel(String title, Label infoLabel, Label loginFailedText, Consumer<String> passwordCallback) {
        super(passwordCallback);
        this.title = title;
        this.infoLabel = infoLabel;
        this.loginFailedText = loginFailedText;
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

}
