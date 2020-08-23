package ru.crystals.pos.bl.sale.payment;

import org.springframework.stereotype.Component;
import ru.crystals.pos.bl.ScenarioManager;
import ru.crystals.pos.bl.api.listener.VoidListener;
import ru.crystals.pos.bl.api.payment.PaymentPluginInArg;
import ru.crystals.pos.bl.api.payment.PaymentPluginScenario;
import ru.crystals.pos.bl.api.scenarios.special.ForceImpossibleException;
import ru.crystals.pos.bl.api.spinner.CallableSpinnerArg;
import ru.crystals.pos.bl.common.CallableSpinner;
import ru.crystals.pos.docs.data.Payment;
import ru.crystals.pos.ui.UI;
import ru.crystals.pos.ui.callback.InteractiveValueCancelledCallback;
import ru.crystals.pos.ui.forms.message.MessageFormModel;
import ru.crystals.pos.ui.forms.sale.PaymentAmountModel;

import java.math.BigDecimal;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

@Component
public class BankPaymentScenario  implements PaymentPluginScenario {

    public static final String BANK = "Банк";
    private static final String BANK_TYPE = "bank";
    private UI ui;
    private final ScenarioManager scenarioManager;

    public BankPaymentScenario(ScenarioManager scenarioManager) {
        this.scenarioManager = scenarioManager;
    }

    @Override
    public void start(UI ui, PaymentPluginInArg inArg, Consumer<Payment> onComplete, VoidListener onCancel) {
        PaymentAmountModel paymentAmountModel = new PaymentAmountModel("Оплата банковской картой", BigDecimal.valueOf(0, 2),
            callback -> formConsumer(callback, onComplete, onCancel));
        ui.showForm(paymentAmountModel);
    }

    private void formConsumer(InteractiveValueCancelledCallback<BigDecimal> callback, Consumer<Payment> onComplete, VoidListener onCancel) {
        switch (callback.getAction()) {
            case CHANGED:
                break;
            case ENTERED:
                valueEntered(callback.getValue(), onComplete, onCancel);
                break;
            case CANCELLED:
                onCancel.call();
                break;
        }
    }

    private void valueEntered(BigDecimal value, Consumer<Payment> onComplete, VoidListener onCancel) {
        if (checkValue(value)) {
            startBankAuthorization(value, onComplete, onCancel);
        }
    }

    private void startBankAuthorization(BigDecimal value, Consumer<Payment> onComplete, VoidListener onCancel) {
        CallableSpinnerArg<BigDecimal> arg = new CallableSpinnerArg<>("Зри в терминал ...", bankAuthorization(value));
        scenarioManager.startChildAsync(new CallableSpinner<>(), arg, bd -> onAuthComplete(bd, onComplete), e -> onAuthError(e, onCancel));
    }

    private Callable<BigDecimal> bankAuthorization(BigDecimal value) {
        return () -> {
            try {
                Thread.sleep(1500L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return BigDecimal.valueOf(9998, 2);
        };
    }

    private void onAuthComplete(BigDecimal summ, Consumer<Payment> onComplete) {
        onComplete.accept(createPayment(summ));
    }

    private void onAuthError(Exception e, VoidListener onCancel) {
        ui.showForm(new MessageFormModel("Ошибка оплаты", onCancel::call));
    }

    private Payment createPayment(BigDecimal value) {
        return new Payment(BANK, value);
    }

    private boolean checkValue(BigDecimal value) {
        return value != null && BigDecimal.ZERO.compareTo(value) < 0;
    }

    @Override
    public String getPaymentTypeName() {
        return BANK_TYPE;
    }

    @Override
    public void tryToCancel() throws ForceImpossibleException {
    }
}
