package ru.crystals.pos.ui;

import ru.crystals.pos.ui.forms.UIFormModel;

public interface UI {

    void setLocale(Locale locale);

    /**
     * Установить текущий слой
     * @param uiLayer слой
     */
    void setLayer(UILayer uiLayer);

    /**
     * Получить текущий слой
     * @return слой
     */
    UILayer getCurrentLayer();

    /**
     * Показать форму в текущем слое
     * @param uiForm модель формы
     */
    void showForm(UIFormModel uiForm);

    /**
     * Передать модели для слоя
     * @param uiLayer слой
     * @param models модели
     */
    void setLayerModels(UILayer uiLayer, UIFormModel... models);

    /**
     * Показать форму в указанном слое
     * Слой будет сменен на указанный, если он еще не активен.
     * @param uiLayer слой
     * @param uiForm модель формы
     */
    void showForm(UILayer uiLayer, UIFormModel uiForm);

}
