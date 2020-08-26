package ru.crystals.pos.screensaver;

import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.crystals.pos.bl.ScenarioManager;
import ru.crystals.pos.hw.events.HumanEvent;
import ru.crystals.pos.ui.UILayer;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
public class ScreenSaverTimer {

    private static final long SCREEN_SAVER_TIMEOUT = 30000;
    private final ScenarioManager scenarioManager;
    private long lastEventTimestamp = System.currentTimeMillis();

    public ScreenSaverTimer(ScenarioManager scenarioManager) {
        this.scenarioManager = scenarioManager;
    }

    @PostConstruct
    private void init() {
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(this::oTimer, 3, 3, TimeUnit.SECONDS);
    }

    @Order(Ordered.HIGHEST_PRECEDENCE)
    @EventListener
    void onHumanEvent(HumanEvent humanEvent) {
        this.lastEventTimestamp = System.currentTimeMillis();
        if (scenarioManager.getCurrentLayer() == UILayer.SCREEN_SAVER) {
            scenarioManager.setLayer(UILayer.LOGIN);
        }
    }

    private void oTimer() {
        if (System.currentTimeMillis() - lastEventTimestamp > SCREEN_SAVER_TIMEOUT && scenarioManager.getCurrentLayer() != UILayer.SCREEN_SAVER) {
            scenarioManager.setLayer(UILayer.SCREEN_SAVER);
        }
    }


}
