package ru.crystals.pos.sco;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import ru.crystals.pos.bl.ScenarioManager;
import ru.crystals.pos.bl.events.ShowMessageScenario;
import ru.crystals.pos.hw.events.HumanEvent;
import ru.crystals.pos.ui.UILayer;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
public class RemoveGoodsFromScaleSpammer {

    private static final long IDLE_MAX_TIME_MS = 15000;

    private final ScenarioManager scenarioManager;
    private long lastEventTimeStamp;

    public RemoveGoodsFromScaleSpammer(ScenarioManager scenarioManager) {
        this.scenarioManager = scenarioManager;
    }

    @PostConstruct
    private void init() {
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(this::onTimer, 3, 2, TimeUnit.SECONDS);
    }

    @EventListener
    @Async
    void onHumanEvent(HumanEvent humanEvent) {
        this.lastEventTimeStamp = System.currentTimeMillis();
    }

    private void onTimer() {
        if ((System.currentTimeMillis() - lastEventTimeStamp) > IDLE_MAX_TIME_MS && scenarioManager.getCurrentLayer() == UILayer.SALE) {
            UILayer prevLayer = scenarioManager.getCurrentLayer();
            scenarioManager.setLayer(UILayer.POPUP);
            scenarioManager .start(new ShowMessageScenario(), "Уберите товар с весов :)", () -> scenarioManager.setLayer(prevLayer));
        }
    }

}
