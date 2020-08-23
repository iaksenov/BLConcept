package csi.pos.ui;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;
import ru.crystals.pos.bl.events.POSStatusListener;

@Component
public class UIApplicationEventPublisher implements ApplicationEventPublisherAware {

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        POSStatusListener.setUiPublisher(applicationEventPublisher);
    }

}
