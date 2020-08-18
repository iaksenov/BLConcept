package ru.crystals.pos.ui;

import ru.crystals.pos.ui.forms.UIFormModel;
import ru.crystals.pos.ui.forms.UIValueFormModel;

import java.util.Collection;
import java.util.Optional;

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
    void setLayerModels(UILayer uiLayer, Collection<UIFormModel> models);

    /**
     * Синхронное получение значения с формы
     * @param model модель формы
     * @param <V> тип значения
     * @return опционал значения
     */
    <V> Optional<V> getFormValue(UIValueFormModel<V> model);

}
