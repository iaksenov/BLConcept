package ru.crystals.pos.docs;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import ru.crystals.pos.docs.data.Payment;
import ru.crystals.pos.docs.data.Position;
import ru.crystals.pos.docs.data.Purchase;
import ru.crystals.pos.docs.event.PurchaseUpdatedEvent;

import java.math.BigDecimal;

@Service
public class DocModuleImpl implements DocModule {

    private final ApplicationEventPublisher publisher;

    private Purchase currentPurcase;

    public DocModuleImpl(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
        this.currentPurcase = new Purchase();
    }

    @Override
    public void addPosition(Position position) {
        System.out.println("Added position: " + position);
        currentPurcase.getPositions().add(position);
        publisher.publishEvent(new PurchaseUpdatedEvent(currentPurcase));
    }

    @Override
    public void addPayment(Payment payment) {
        System.out.println("Added payment: " + payment);
        currentPurcase.getPayments().add(payment);
        publisher.publishEvent(new PurchaseUpdatedEvent(currentPurcase));
    }

    @Override
    public Purchase getCurrentPurchase() {
        return currentPurcase;
    }

    @Override
    public void purchaseRegistered(Purchase purchase) {
        this.currentPurcase = new Purchase();
        publisher.publishEvent(new PurchaseUpdatedEvent(currentPurcase));
    }

    @Override
    public void setDiscountAmount(BigDecimal amount) {
        this.currentPurcase.setDiscountAmount(amount);
        publisher.publishEvent(new PurchaseUpdatedEvent(currentPurcase));
    }

}
