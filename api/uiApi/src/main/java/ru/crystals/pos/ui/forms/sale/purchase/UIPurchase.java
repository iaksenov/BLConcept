package ru.crystals.pos.ui.forms.sale.purchase;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class UIPurchase {

    private final List<UIPosition> positions = new ArrayList<>();

    private final List<UIPayment> payments = new ArrayList<>();

    private BigDecimal discountAmount = null;

    public UIPurchase() {
    }

    public List<UIPosition> getPositions() {
        return positions;
    }

    public List<UIPayment> getPayments() {
        return payments;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }
}
