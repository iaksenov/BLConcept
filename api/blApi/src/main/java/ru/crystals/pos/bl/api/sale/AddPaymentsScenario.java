package ru.crystals.pos.bl.api.sale;

import ru.crystals.pos.bl.api.OutScenario;

public interface AddPaymentsScenario extends OutScenario<Void> {

    void setPreferredPayment(String paymentName);

}
