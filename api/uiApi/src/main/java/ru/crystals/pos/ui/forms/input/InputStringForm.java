package ru.crystals.pos.ui.forms.input;

import ru.crystals.pos.ui.callback.TextCallback;
import ru.crystals.pos.ui.forms.UIForm;

public class InputStringForm implements UIForm {

    private String title;
    private String hint;
    private TextCallback callback;

    public InputStringForm(String title, String hint, TextCallback callback) {
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

    public TextCallback getCallback() {
        return callback;
    }

}
