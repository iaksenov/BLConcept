package ru.crystals.pos.ui;

import ru.crystals.pos.hw.events.listeners.key.ControlKey;
import ru.crystals.pos.hw.events.listeners.key.TypedKey;
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
     * @param <U> тип формы
     * @return объект модели формы
     */
    <U extends UIFormModel> U showForm(U uiForm);

    /**
     * Показать форму в указанном слое. Слой будет сменен на указанный, если он еще не активен.
     * @param uiLayer слой
     * @param uiForm модель формы
     * @param <U> тип формы
     * @return объект модели формы
     */
    <U extends UIFormModel> U showForm(UILayer uiLayer, U uiForm);

    void dispatchControlKey(ControlKey controlKey);

    void dispatchTypedKey(TypedKey controlKey);

}
