package csi.pos.ui.swing.forms;

import org.springframework.stereotype.Component;
import ru.crystals.pos.ui.forms.UIFormModel;
import ru.crystals.pos.ui.forms.UIModelListener;

import javax.swing.JComponent;

/**
 * Базовый класс формы.
 * Singleton.
 * Наследники являются spring prototype.
 * Порядок вызовов извне:
 * 1. create() для создания UI
 * 2. onModelChanged(model)
 * @param <T>
 */
@Component
public abstract class Form<T extends UIFormModel> implements UIModelListener<T> {

    /**
     * Создать Swing UI компонент
     * @return компонент
     */
    public abstract JComponent create();

    /**
     * Получить класс модели. Используется для FormsCatalog.
     * @return класс модели
     */
    public abstract Class<T> getModelClass();

}
