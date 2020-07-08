package ru.crystals.pos.bl.api.sale;

import ru.crystals.pos.bl.api.CompletedScenario;
import ru.crystals.pos.hw.events.listeners.BarcodeListener;
import ru.crystals.pos.hw.events.listeners.MSRListener;

public interface SaleAddItemsScenario extends CompletedScenario<Void>, BarcodeListener, MSRListener {

    /**
     * Если можно завершиться, то выполнить необходимое и вызвать onComplete
     */
    void doFinish();

}
