package ru.crystals.pos.ui;

import ru.crystals.pos.ui.forms.UIFormModel;

public interface UI {

    void setLocale(Locale locale);

    /**
     * Установить текущий слой
     * @param uiLayer
     */
    void setLayer(UILayer uiLayer);

    /**
     * Показать форму в текущем слое.
     * @param uiForm модель формы
     */
    void showForm(UIFormModel uiForm);

    /**
     * Показать форму в указанном слое. Слой будет сменен на указанный, если он еще не активен.
     * @param uiLayer слой
     * @param uiForm модель формы
     */
    void showForm(UILayer uiLayer, UIFormModel uiForm);

}
