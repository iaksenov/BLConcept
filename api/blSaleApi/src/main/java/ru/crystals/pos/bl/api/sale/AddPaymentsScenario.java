package ru.crystals.pos.bl.api.sale;

import ru.crystals.pos.bl.api.listener.VoidListener;
import ru.crystals.pos.bl.api.scenarios.InCompleteCancelScenario;

public interface AddPaymentsScenario extends InCompleteCancelScenario<String> {

    void start(String preferredPaymentType, VoidListener onComplete, VoidListener onCancel);

}
