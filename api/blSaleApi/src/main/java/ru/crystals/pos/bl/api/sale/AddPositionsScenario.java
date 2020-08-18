package ru.crystals.pos.bl.api.sale;

import ru.crystals.pos.bl.api.scenarios.CompleteScenario;
import ru.crystals.pos.hw.events.listeners.BarcodeListener;
import ru.crystals.pos.hw.events.listeners.MSRListener;

public interface AddPositionsScenario extends CompleteScenario, BarcodeListener, MSRListener {

    /**
     * Поиск товара
     * @param searchString строка поиска
     */
    void onSearchProduct(String searchString);

}
