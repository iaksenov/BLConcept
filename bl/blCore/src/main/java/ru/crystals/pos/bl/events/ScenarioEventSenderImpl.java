package ru.crystals.pos.bl.events;

import org.springframework.stereotype.Service;
import ru.crystals.pos.bl.ScenarioManager;
import ru.crystals.pos.bl.api.scenarios.Scenario;
import ru.crystals.pos.bl.api.scenarios.special.ScenarioEventProcessor;
import ru.crystals.pos.bl.api.scenarios.special.ScenarioIgnoreAllEvents;
import ru.crystals.pos.hw.events.bl.HWBLHumanEvent;
import ru.crystals.pos.hw.events.interceptor.CallbackInterceptor;
import ru.crystals.pos.hw.events.keys.FuncKey;
import ru.crystals.pos.hw.events.listeners.Barcode;
import ru.crystals.pos.hw.events.listeners.BarcodeListener;
import ru.crystals.pos.hw.events.listeners.FuncKeyListener;
import ru.crystals.pos.hw.events.listeners.MSRListener;
import ru.crystals.pos.hw.events.listeners.MSRTracks;

@Service
public class ScenarioEventSenderImpl implements ScenarioEventSender {

    private final ScenarioManager scenarioManager;
    private final EventPreProcessor preProcessor;


    public ScenarioEventSenderImpl(ScenarioManager scenarioManager, EventPreProcessor preProcessor) {
        this.scenarioManager = scenarioManager;
        this.preProcessor = preProcessor;
        CallbackInterceptor.setLockedFunc(this::isIgnoreAllEvents);
    }

    @Override
    public boolean isIgnoreAllEvents() {
        return scenarioManager.getCurrentScenario() instanceof ScenarioIgnoreAllEvents;
    }

    @Override
    public void processEvent(HWBLHumanEvent event) {
        if (!preProcessor.processEvent(event)) {
            Scenario currentScenario = scenarioManager.getCurrentScenario();
            if (event instanceof Barcode) {
                recursiveProcessBarcode(event, currentScenario);
            } else if (event instanceof MSRTracks) {
                recursiveProcessMSR(event, currentScenario);
            } else if (event instanceof FuncKey) {
                recursiveProcessFuncKey(event, currentScenario);
            }
        }
    }

    private void recursiveProcessBarcode(HWBLHumanEvent event, Scenario scenario) {
        if (filterEvent(event, scenario)) {
            return;
        }
        if (scenario instanceof BarcodeListener) {
            ((BarcodeListener)scenario).onBarcode(((Barcode)event).getCode());
        } else {
            if (scenario != null) {
                recursiveProcessBarcode(event, scenarioManager.getParentScenario(scenario));
            }
        }
    }

    private void recursiveProcessMSR(HWBLHumanEvent event, Scenario scenario) {
        if (filterEvent(event, scenario)) {
            return;
        }
        if (scenario instanceof MSRListener) {
            ((MSRListener)scenario).onMSR(((MSRTracks)event));
        } else {
            if (scenario != null) {
                recursiveProcessMSR(event, scenarioManager.getParentScenario(scenario));
            }
        }
    }

    private void recursiveProcessFuncKey(HWBLHumanEvent event, Scenario scenario) {
        if (filterEvent(event, scenario)) {
            return;
        }
        if (scenario instanceof FuncKeyListener) {
            ((FuncKeyListener)scenario).onFunctionalKey((FuncKey) event);
        } else {
            if (scenario != null) {
                recursiveProcessFuncKey(event, scenarioManager.getParentScenario(scenario));
            }
        }
    }

    private boolean filterEvent(HWBLHumanEvent event, Scenario scenario) {
        return scenario instanceof ScenarioEventProcessor && ((ScenarioEventProcessor) scenario).processEvent(event);
    }

}
