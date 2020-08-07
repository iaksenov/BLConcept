package ru.crystals.pos.bl.events;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import ru.crystals.pos.ui.events.POSStatusEvent;
import ru.crystals.pos.user.UserAuthorisedEvent;

@Service
public class POSStatusListener {

    private ApplicationEventPublisher publisher;

    public POSStatusListener(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @EventListener
    private void onUserAuthorised(UserAuthorisedEvent event) {
        POSStatusEvent posStatusEvent = new POSStatusEvent();
        posStatusEvent.setCurrentCashierFIO(getUserFIO(event));
        publisher.publishEvent(posStatusEvent);
    }

    private String getUserFIO(UserAuthorisedEvent event) {
        return event.getPayload().getFirstName() + " " + event.getPayload().getLastName() + "." + event.getPayload().getMiddleName() + ".";
    }

}
