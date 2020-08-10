package ru.crystals.pos.ui.forms.message;

import ru.crystals.pos.ui.forms.UIFormVoidCallbackModel;

public class MessageFormModel extends UIFormVoidCallbackModel {

    private String message;

    public MessageFormModel(String message, Runnable callback) {
        super(callback);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
