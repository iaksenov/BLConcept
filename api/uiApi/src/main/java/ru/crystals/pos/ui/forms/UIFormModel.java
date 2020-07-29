package ru.crystals.pos.ui.forms;

public abstract class UIFormModel {

    private UIModelListener<UIFormModel> uiModelListener;

    public void setListener(UIModelListener<UIFormModel> uiModelListener) {
        this.uiModelListener = uiModelListener;
    }

    public void modelChanged() {
        if (uiModelListener != null) {
            uiModelListener.onModelChanged(this);
        }
    }

}
