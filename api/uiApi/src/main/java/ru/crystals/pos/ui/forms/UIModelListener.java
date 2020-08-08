package ru.crystals.pos.ui.forms;

/**
 * Интерфейс слушателя изменений в модели
 * @param <T>
 */
public interface UIModelListener<T> {

    void onModelChanged(T model);

}
