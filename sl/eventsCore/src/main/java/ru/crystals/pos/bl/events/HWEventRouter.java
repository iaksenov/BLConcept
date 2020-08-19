package ru.crystals.pos.bl.events;

import org.springframework.stereotype.Component;
import ru.crystals.pos.hw.events.HWHumanEvent;
import ru.crystals.pos.hw.events.bl.HWBLHumanEvent;
import ru.crystals.pos.hw.events.keys.ControlKey;
import ru.crystals.pos.hw.events.keys.TypedKey;
import ru.crystals.pos.hw.events.ui.HWUIHumanEvent;
import ru.crystals.pos.ui.UIKeyListener;

/**
 * Роутер hardware событий
 */
@Component
public class HWEventRouter {

    private final ScenarioEventSender scenarioEventSender;

    private final UIKeyListener uiKeyListener;

    public HWEventRouter(ScenarioEventSender scenarioEventSender, UIKeyListener uiKeyListener) {
        this.scenarioEventSender = scenarioEventSender;
        this.uiKeyListener = uiKeyListener;
    }

    public <T extends HWHumanEvent> void processEvent(T event) {
        if (scenarioEventSender.isIgnoreAllEvents()) {
            System.out.println("BL locked. Event " + event + " ignored");
        } else {
            if (event instanceof HWUIHumanEvent) {
                processUIEvent((HWUIHumanEvent)event);
            } else if (event instanceof HWBLHumanEvent) {
                processBLEvent((HWBLHumanEvent)event);
            }
        }
    }

    // send to BL
    private void processBLEvent(HWBLHumanEvent event) {
        scenarioEventSender.processEvent(event);
    }

    // send to UI
    private void processUIEvent(HWUIHumanEvent event) {
        if (event instanceof TypedKey) {
            uiKeyListener.onTypedKey((TypedKey) event);
        } else if (event instanceof ControlKey) {
            uiKeyListener.onControlKey((ControlKey)event);
        }
    }

}


