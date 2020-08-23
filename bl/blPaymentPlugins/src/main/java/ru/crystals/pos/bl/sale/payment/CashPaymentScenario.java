package ru.crystals.pos.bl.sale.payment;

import org.springframework.stereotype.Component;
import ru.crystals.pos.bl.api.listener.VoidListener;
import ru.crystals.pos.bl.api.payment.PaymentPluginInArg;
import ru.crystals.pos.bl.api.payment.PaymentPluginScenario;
import ru.crystals.pos.bl.api.scenarios.special.ForceImpossibleException;
import ru.crystals.pos.docs.data.Payment;
import ru.crystals.pos.ui.UI;
import ru.crystals.pos.ui.callback.InteractiveValueCancelledCallback;
import ru.crystals.pos.ui.forms.sale.PaymentAmountModel;

import java.math.BigDecimal;
import java.util.function.Consumer;

@Component
public class CashPaymentScenario implements PaymentPluginScenario {

    public static final String CASH = "Наличные";
    private static final String CASH_TYPE = "cash";

    @Override
    public void start(UI ui, PaymentPluginInArg inArg, Consumer<Payment> onComplete, VoidListener onCancel) {
        PaymentAmountModel paymentAmountModel = new PaymentAmountModel("Оплата наличными", BigDecimal.valueOf(0, 2),
            callback -> formConsumer(callback, onComplete, onCancel));
        ui.showForm(paymentAmountModel);
    }

    private void formConsumer(InteractiveValueCancelledCallback<BigDecimal> callback, Consumer<Payment> onComplete, VoidListener onCancel) {
        switch (callback.getAction()) {
            case CHANGED:
                break;
            case ENTERED:
                valueEntered(callback.getValue(), onComplete);
                break;
            case CANCELLED:
                onCancel.call();
                break;
        }
    }

    private void valueEntered(BigDecimal value, Consumer<Payment> onComplete) {
        if (checkValue(value)) {
            onComplete.accept(createPayment(value));
        }
    }

    private Payment createPayment(BigDecimal value) {
        return new Payment(CASH, value);
    }

    private boolean checkValue(BigDecimal value) {
        return value != null && BigDecimal.ZERO.compareTo(value) < 0;
    }

    @Override
    public String getPaymentTypeName() {
        return CASH_TYPE;
    }

    @Override
    public void tryToCancel() throws ForceImpossibleException {
    }
}
