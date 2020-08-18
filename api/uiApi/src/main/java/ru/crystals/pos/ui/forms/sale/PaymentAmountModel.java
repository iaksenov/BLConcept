package ru.crystals.pos.ui.forms.sale;

import ru.crystals.pos.ui.callback.InteractiveValueCancelledCallback;
import ru.crystals.pos.ui.forms.UIValueCallbackFormModel;

import java.math.BigDecimal;
import java.util.function.Consumer;

public class PaymentAmountModel extends UIValueCallbackFormModel<BigDecimal, InteractiveValueCancelledCallback<BigDecimal>>  {

    private String paymentName;
    private BigDecimal value;

    public PaymentAmountModel(String paymentName, BigDecimal value, Consumer<InteractiveValueCancelledCallback<BigDecimal>> consumer) {
        super(consumer);
        this.paymentName = paymentName;
        this.value = value;
    }

    public String getPaymentName() {
        return paymentName;
    }

    public BigDecimal getValue() {
        return value;
    }
}
