package ru.crystals.pos.ui.forms.sale.purchase;

import java.math.BigDecimal;

public class UIPosition {

    private final String name;

    private final String item;

    private final BigDecimal price;

    private final BigDecimal count;

    public UIPosition(String name, String item, BigDecimal price, BigDecimal count) {
        this.name = name;
        this.item = item;
        this.price = price;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public String getItem() {
        return item;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getCount() {
        return count;
    }

    @Override
    public String toString() {
        return name + " x" + count;
    }
}
