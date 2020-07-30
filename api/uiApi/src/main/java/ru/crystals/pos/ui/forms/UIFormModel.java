package ru.crystals.pos.ui.forms;

public abstract class UIFormModel {

    private UIModelListener<UIFormModel> modelListener;

    public void setListener(UIModelListener<UIFormModel> modelListener) {
        this.modelListener = modelListener;
    }

    public void modelChanged() {
        if (modelListener != null) {
            modelListener.onModelChanged(this);
        }
    }

}
