package ru.crystals.pos.bl.api.payment;

import java.math.BigDecimal;

/**
 * Аргумент для запуска сценариев оплаты
 */
public class PaymentPluginInArg {

    private final BigDecimal summ;

    public PaymentPluginInArg(BigDecimal summ) {
        this.summ = summ;
    }

    public BigDecimal getSumm() {
        return summ;
    }

}
