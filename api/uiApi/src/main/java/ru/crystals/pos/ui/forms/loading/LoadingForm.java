package ru.crystals.pos.ui.forms.loading;

import ru.crystals.pos.ui.forms.UIForm;

import java.util.stream.Stream;

public class LoadingForm implements UIForm {

    private String caption;

    private Stream version;

    public LoadingForm(String caption, Stream version) {
        this.caption = caption;
        this.version = version;
    }

}
