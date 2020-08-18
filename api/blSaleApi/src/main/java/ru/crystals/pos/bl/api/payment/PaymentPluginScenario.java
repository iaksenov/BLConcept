package ru.crystals.pos.bl.api.payment;

import ru.crystals.pos.bl.api.scenarios.InOutCancelScenario;
import ru.crystals.pos.docs.data.Payment;

public interface PaymentPluginScenario extends InOutCancelScenario<PaymentPluginInArg, Payment> {

    String getPaymentTypeName();

}
