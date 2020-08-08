package ru.crystals.pos.ui.forms.sale;

import ru.crystals.pos.ui.forms.UIFormModel;

import java.util.List;
import java.util.function.Consumer;

public class PlitkiFormModel extends UIFormModel {

    private final List<String> plitki;
    private Consumer<String> selected;

    public PlitkiFormModel(List<String> plitki, Consumer<String> selected) {
        this.plitki = plitki;
        this.selected = selected;
    }

    public List<String> getPlitki() {
        return plitki;
    }

    public Consumer<String> getSelected() {
        return selected;
    }

    public void setSelected(Consumer<String> selected) {
        this.selected = selected;
    }

}
