package ru.crystals.pos.bl.api.sale;

import ru.crystals.pos.bl.api.InCompleteCancelScenario;
import ru.crystals.pos.bl.api.listener.VoidListener;

public interface AddPaymentsScenario extends InCompleteCancelScenario<String> {

    void start(String prefferedPaymentType, VoidListener onComplete, VoidListener onCancel);

}
