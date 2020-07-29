package ru.crystals.pos.ui.forms.loading;

import ru.crystals.pos.ui.forms.UIFormModel;

public class LoadingFormModel extends UIFormModel {

    private String caption;

    private String version;

    public LoadingFormModel(String caption, String version) {
        this.caption = caption;
        this.version = version;
    }

    public String getCaption() {
        return caption;
    }

    public String getVersion() {
        return version;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
}
