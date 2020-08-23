package ru.crystals.pos.bl.api.sale;

import ru.crystals.pos.bl.api.scenarios.InOutScenario;
import ru.crystals.pos.docs.data.Purchase;
import ru.crystals.pos.ui.forms.sale.purchase.PurchaseStages;

public interface RegisterPurchaseScenario extends InOutScenario<Purchase, RegisterPurchaseResult>, PurchaseStage {

    default PurchaseStages getPurchaseStage() {
        return PurchaseStages.REG;
    }

}
