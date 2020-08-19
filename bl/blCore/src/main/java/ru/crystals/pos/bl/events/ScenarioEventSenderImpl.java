package ru.crystals.pos.bl.events;

import org.springframework.stereotype.Service;
import ru.crystals.pos.bl.ScenarioManager;
import ru.crystals.pos.bl.api.marker.IgnoreAllEvents;
import ru.crystals.pos.bl.api.scenarios.Scenario;
import ru.crystals.pos.bl.api.scenarios.special.ScenarioEventFilter;
import ru.crystals.pos.hw.events.HWHumanEvent;
import ru.crystals.pos.hw.events.interceptor.CallbackInterceptor;
import ru.crystals.pos.hw.events.keys.FuncKey;
import ru.crystals.pos.hw.events.listeners.*;

@Service
public class ScenarioEventSenderImpl implements ScenarioEventSender {

    private final ScenarioManager scenarioManager;
    private final EventPreProcessor preProcessor;

    public ScenarioEventSenderImpl(ScenarioManager scenarioManager, EventPreProcessor preProcessor) {
        this.scenarioManager = scenarioManager;
        this.preProcessor = preProcessor;
        CallbackInterceptor.setLockedFunc(this::isIgnoreCurrentEvents);
    }

    @Override
    public void processEvent(HWHumanEvent event) {
        if (!preProcessor.processEvent(event)) {
            if (event instanceof Barcode) {
                onBarcode(event);
            }
        }
    }

    public void onBarcode(HWHumanEvent event) {
        processBarcode(event, scenarioManager.getCurrentScenario());
    }

    private void processBarcode(HWHumanEvent event, Scenario scenario) {
        boolean processed = false;
        if (scenario instanceof ScenarioEventFilter) {
            processed = ((ScenarioEventFilter) scenario).filterEvent(event);
        }
        if ()
        if (scenario instanceof BarcodeListener) {
            ((BarcodeListener)scenario).onBarcode(((Barcode)event).getCode());
        } else {
            processBarcode(event, scenarioManager.getParentScenario(scenario));
        }
    }

    @Override
    public void onFunctionalKey(FuncKey funcKey) {
        processFunctionalKey(funcKey, scenarioManager.getCurrentScenario());
    }

    private void processFunctionalKey(FuncKey funcKey, Scenario scenario) {
        if (scenario instanceof BarcodeListener) {
            ((FuncKeyListener)scenario).onFunctionalKey(funcKey);
        } else {
            processFunctionalKey(funcKey, scenarioManager.getParentScenario(scenario));
        }
    }

    @Override
    public void onMSR(MSRTracks msrTracks) {
        processMSR(msrTracks, scenarioManager.getCurrentScenario());
    }

    @Override
    public boolean isIgnoreCurrentEvents() {
        return scenarioManager.getCurrentScenario() instanceof IgnoreAllEvents;
    }

    private void processMSR(MSRTracks msrTracks, Scenario scenario) {
        if (scenario instanceof MSRListener) {
            ((MSRListener)scenario).onMSR(msrTracks);
        } else {
            processMSR(msrTracks, scenarioManager.getParentScenario(scenario));
        }
    }

}
