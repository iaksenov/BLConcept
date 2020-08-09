package ru.crystals.pos.bl.api.sale;

import ru.crystals.pos.bl.api.OutScenario;
import ru.crystals.pos.hw.events.listeners.BarcodeListener;
import ru.crystals.pos.hw.events.listeners.MSRListener;

public interface SaleAddItemsScenario extends OutScenario<Void>, BarcodeListener, MSRListener {

    void showProductPlugin(String s);

    /**
     * Если можно завершиться, то выполнить необходимое и вызвать onComplete
     */
    void doFinish();

}
