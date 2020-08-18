package ru.crystals.pos.docs.data;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Purchase {

    private final List<Position> positions = new ArrayList<>();

    private final List<Payment> payments = new ArrayList<>();

    private BigDecimal discountAmount = null;

    public Purchase() {
    }

    public List<Position> getPositions() {
        return positions;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }
}
