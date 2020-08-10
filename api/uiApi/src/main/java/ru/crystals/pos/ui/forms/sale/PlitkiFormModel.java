package ru.crystals.pos.ui.forms.sale;

import ru.crystals.pos.ui.forms.UIFormCallbackModel;

import java.util.List;
import java.util.function.Consumer;

public class PlitkiFormModel extends UIFormCallbackModel<String> {

    private final List<String> plitki;

    public PlitkiFormModel(List<String> plitki, Consumer<String> selected) {
        super(selected);
        this.plitki = plitki;
    }

    public List<String> getPlitki() {
        return plitki;
    }

}
