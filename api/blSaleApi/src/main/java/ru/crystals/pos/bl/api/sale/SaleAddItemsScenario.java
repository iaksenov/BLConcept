package ru.crystals.pos.bl.api.sale;

import ru.crystals.pos.bl.api.CompleteScenario;
import ru.crystals.pos.hw.events.listeners.BarcodeListener;
import ru.crystals.pos.hw.events.listeners.MSRListener;

public interface SaleAddItemsScenario extends CompleteScenario, BarcodeListener, MSRListener {

    /**
     * Поиск товара
     * @param searchString строка поиска
     */
    void searchProduct(String searchString);

    /**
     * Если можно завершиться, то выполнить необходимое и вызвать onComplete
     */
    void doFinish();

}
