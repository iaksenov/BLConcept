package ru.crystals.pos.ui.forms.input;

import ru.crystals.pos.ui.forms.UIFormModel;

import java.util.function.Consumer;

public class InputStringFormModel extends UIFormModel {

    private String title;
    private String hint;
    private Consumer<String> callback;

    public InputStringFormModel(String title, String hint, Consumer<String> callback) {
        this.title = title;
        this.hint = hint;
        this.callback = callback;
    }

    public String getTitle() {
        return title;
    }

    public String getHint() {
        return hint;
    }

    public Consumer<String> getCallback() {
        return callback;
    }

}
