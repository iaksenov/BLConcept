package ru.crystals.pos.ui.forms.sale.purchase;

import java.math.BigDecimal;

public class UIPayment {

    private final String typeName;

    private final BigDecimal amount;

    public UIPayment(String typeName, BigDecimal amount) {
        this.typeName = typeName;
        this.amount = amount;
    }

    public String getTypeName() {
        return typeName;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
