package ru.crystals.pos.ui.forms;

public interface UIModelListener<T extends UIFormModel> {

    void onModelChanged(T model);

}
