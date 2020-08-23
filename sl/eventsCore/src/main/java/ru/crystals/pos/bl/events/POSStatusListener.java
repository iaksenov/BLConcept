package ru.crystals.pos.bl.events;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import ru.crystals.pos.ui.events.POSStatusEvent;
import ru.crystals.pos.user.UserAuthorisedEvent;

@Service
public class POSStatusListener {

    // из контекста UI
    private static ApplicationEventPublisher uiPublisher;
    private POSStatusEvent lastEvent = new POSStatusEvent();

    public static void setUiPublisher(ApplicationEventPublisher uiPublisher) {
        POSStatusListener.uiPublisher = uiPublisher;
    }

    @EventListener
    private void onUserAuthorised(UserAuthorisedEvent event) {
        lastEvent.setCurrentCashierFIO(getUserFIO(event));
        publishToUi(lastEvent);
    }

    private void publishToUi(POSStatusEvent posStatusEvent) {
        if (uiPublisher != null) {
            uiPublisher.publishEvent(posStatusEvent);
        }
    }

    private String getUserFIO(UserAuthorisedEvent event) {
        if (event.getUser() == null) {
            return "Нет кассира";
        } else {
            return event.getUser().getFirstName() + " " + event.getUser().getLastName() + "." + event.getUser().getMiddleName() + ".";
        }
    }

}
