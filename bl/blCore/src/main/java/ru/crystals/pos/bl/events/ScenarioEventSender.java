package ru.crystals.pos.bl.events;

import org.springframework.stereotype.Service;
import ru.crystals.pos.bl.ScenarioManager;
import ru.crystals.pos.bl.api.Scenario;
import ru.crystals.pos.hw.events.keys.FuncKey;
import ru.crystals.pos.hw.events.listeners.BarcodeListener;
import ru.crystals.pos.hw.events.listeners.FuncKeyListener;
import ru.crystals.pos.hw.events.listeners.MSRListener;
import ru.crystals.pos.hw.events.listeners.MSRTracks;

@Service
public class ScenarioEventSender {

    private ScenarioManager scenarioManager;

    public ScenarioEventSender(ScenarioManager scenarioManager) {
        this.scenarioManager = scenarioManager;
    }

    void onBarcode(String code) {
        // preProcessor
        processBarcode(code, scenarioManager.getCurrentScenario());
    }

    private void processBarcode(String code, Scenario scenario) {
        if (scenario instanceof BarcodeListener) {
            ((BarcodeListener)scenario).onBarcode(code);
        } else {
            processBarcode(code, scenarioManager.getParentScenario(scenario));
        }
    }

    void onFunctionalKey(FuncKey funcKey) {
        // preProcessor
        processFunctionalKey(funcKey, scenarioManager.getCurrentScenario());
    }

    private void processFunctionalKey(FuncKey funcKey, Scenario scenario) {
        if (scenario instanceof BarcodeListener) {
            ((FuncKeyListener)scenario).onFunctionalKey(funcKey);
        } else {
            processFunctionalKey(funcKey, scenarioManager.getParentScenario(scenario));
        }
    }

    void onMSR(MSRTracks msrTracks) {
        // preProcessor
        processMSR(msrTracks, scenarioManager.getCurrentScenario());
    }

    private void processMSR(MSRTracks msrTracks, Scenario scenario) {
        if (scenario instanceof MSRListener) {
            ((MSRListener)scenario).onMSR(msrTracks);
        } else {
            processMSR(msrTracks, scenarioManager.getParentScenario(scenario));
        }
    }

}
