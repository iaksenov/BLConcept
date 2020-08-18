package ru.crystals.pos.docs;

import ru.crystals.pos.docs.data.Payment;
import ru.crystals.pos.docs.data.Position;
import ru.crystals.pos.docs.data.Purchase;

import java.math.BigDecimal;

public interface DocModule {

    void addPosition(Position position);

    void addPayment(Payment payment);

    Purchase getCurrentPurchase();

    void purchaseRegistered(Purchase purchase);

    void setDiscountAmount(BigDecimal amount);

}
