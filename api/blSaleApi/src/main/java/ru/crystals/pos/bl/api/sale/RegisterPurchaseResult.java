package ru.crystals.pos.bl.api.sale;

import ru.crystals.pos.docs.data.Purchase;

public class RegisterPurchaseResult {

    private final Purchase purchase;

    public RegisterPurchaseResult(Purchase purchase) {
        this.purchase = purchase;
    }

    public Purchase getPurchase() {
        return purchase;
    }
}
