package ru.crystals.pos.ui.forms;

public abstract class UIFormModel {

    private UIModelListener modelListener;

    /**
     * Подписаться на изменение модели.
     * @param modelListener слушатель
     * @param <T> тип модели
     */
    public <T extends UIFormModel> void setListener(UIModelListener<T> modelListener) {
        this.modelListener = modelListener;
    }

    /**
     * Уведомление UI об изменении модели
     */
    public <T> void modelChanged() {
        if (modelListener != null) {
            modelListener.onModelChanged(this);
        }
    }

}
