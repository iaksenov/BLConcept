package ru.crystals.pos.docs.data;

import java.math.BigDecimal;

public class Payment {

    private String typeName;

    private BigDecimal summ;

    public Payment(String typeName, BigDecimal summ) {
        this.typeName = typeName;
        this.summ = summ;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public BigDecimal getSumm() {
        return summ;
    }

    public void setSumm(BigDecimal summ) {
        this.summ = summ;
    }
}
