package ru.crystals.pos.ui.forms.message;

import ru.crystals.pos.ui.callback.ConfirmCallback;
import ru.crystals.pos.ui.forms.UIFormModel;

public class MessageFormModel extends UIFormModel {

    private String message;
    private ConfirmCallback confirm;

    public MessageFormModel(String message, ConfirmCallback confirm) {
        this.message = message;
        this.confirm = confirm;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ConfirmCallback getConfirm() {
        return confirm;
    }

    public void setConfirm(ConfirmCallback confirm) {
        this.confirm = confirm;
    }
}
