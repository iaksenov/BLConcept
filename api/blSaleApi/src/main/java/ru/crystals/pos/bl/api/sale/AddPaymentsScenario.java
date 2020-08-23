package ru.crystals.pos.bl.api.sale;

import ru.crystals.pos.bl.api.scenarios.InCompleteCancelScenario;
import ru.crystals.pos.ui.forms.sale.purchase.PurchaseStages;

public interface AddPaymentsScenario extends InCompleteCancelScenario<String>, PurchaseStage {

    void changePaymentType(String paymentType);

    default PurchaseStages getPurchaseStage() {
        return PurchaseStages.PAY;
    }
}
