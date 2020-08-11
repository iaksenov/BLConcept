package ru.crystals.pos.ui.forms.input;

import ru.crystals.pos.ui.forms.UIFormCallbackModel;

import java.util.function.Consumer;

public class InputStringFormModel extends UIFormCallbackModel<String> {

    private String title;
    private String hint;

    public InputStringFormModel(String title, String hint, Consumer<String> callback) {
        super(callback);
        this.title = title;
        this.hint = hint;
    }

    public String getTitle() {
        return title;
    }

    public String getHint() {
        return hint;
    }

}
