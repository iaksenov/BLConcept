package ru.crystals.pos.ui.forms.spinner;

import ru.crystals.pos.ui.forms.UIFormModel;

public class SpinnerModel extends UIFormModel {

    private String message;

    public SpinnerModel(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
