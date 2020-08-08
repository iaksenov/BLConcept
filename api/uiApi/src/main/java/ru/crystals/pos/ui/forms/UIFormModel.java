package ru.crystals.pos.ui.forms;

public abstract class UIFormModel {

    private UIModelListener modelListener;

    public <T extends UIFormModel> void setListener(UIModelListener<T> modelListener) {
        this.modelListener = modelListener;
    }

    public void modelChanged() {
        if (modelListener != null) {
            modelListener.onModelChanged(this);
        }
    }

}
