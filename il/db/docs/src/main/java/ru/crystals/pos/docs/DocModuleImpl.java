package ru.crystals.pos.docs;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import ru.crystals.pos.docs.data.Position;
import ru.crystals.pos.docs.data.Purchase;
import ru.crystals.pos.docs.event.PurchaseUpdatedEvent;

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

}
