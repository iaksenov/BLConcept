package ru.crystals.pos.docs.event;

import ru.crystals.pos.docs.data.Purchase;

/**
 * Сообщение об изменении чека
 */
public class PurchaseUpdatedEvent {

    private final Purchase purchase;

    public PurchaseUpdatedEvent(Purchase purchase) {
        this.purchase = purchase;
    }

    public Purchase getPurchase() {
        return purchase;
    }

}
