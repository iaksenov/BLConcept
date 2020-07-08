package ru.crystals.pos.bl.api.sale;

import ru.crystals.pos.bl.api.CompletedScenario;

public interface AddPaymentsScenario extends CompletedScenario<Void> {

    void setPreferredPayment(String paymentName);

}
