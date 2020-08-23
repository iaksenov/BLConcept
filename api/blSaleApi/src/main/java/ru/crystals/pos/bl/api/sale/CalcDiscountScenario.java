package ru.crystals.pos.bl.api.sale;

import ru.crystals.pos.bl.api.scenarios.CompleteCancelScenario;
import ru.crystals.pos.ui.forms.sale.purchase.PurchaseStages;

public interface CalcDiscountScenario extends CompleteCancelScenario, PurchaseStage {

    default PurchaseStages getPurchaseStage() {
        return PurchaseStages.CALC;
    }

}
