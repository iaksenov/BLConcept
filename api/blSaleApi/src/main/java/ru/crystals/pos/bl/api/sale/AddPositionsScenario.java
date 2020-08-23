package ru.crystals.pos.bl.api.sale;

import ru.crystals.pos.bl.api.scenarios.InScenario;
import ru.crystals.pos.hw.events.listeners.BarcodeListener;
import ru.crystals.pos.hw.events.listeners.MSRListener;
import ru.crystals.pos.ui.forms.sale.purchase.PurchaseStages;

public interface AddPositionsScenario extends InScenario<String>, BarcodeListener, MSRListener, PurchaseStage {

    default PurchaseStages getPurchaseStage() {
        return PurchaseStages.ADD;
    }

    void searchProduct(String searchString);
}
