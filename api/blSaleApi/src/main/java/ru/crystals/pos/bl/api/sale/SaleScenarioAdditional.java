package ru.crystals.pos.bl.api.sale;

import ru.crystals.pos.ui.forms.UIFormModel;

import java.util.Collection;

/**
 * Интерфейс расширений сценария продажи
 */
public interface SaleScenarioAdditional {

    Collection<UIFormModel> getAdditionalModels();

}
