package ru.crystals.pos.bl.events;

import org.springframework.stereotype.Component;
import ru.crystals.pos.hw.events.HWEventPayload;
import ru.crystals.pos.hw.events.keys.ControlKey;
import ru.crystals.pos.hw.events.keys.FuncKey;
import ru.crystals.pos.hw.events.keys.TypedKey;
import ru.crystals.pos.hw.events.listeners.Barcode;
import ru.crystals.pos.hw.events.listeners.MSRTracks;
import ru.crystals.pos.ui.UIKeyListener;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Роутер hardware событий
 */
@Component
public class HWEventRouter {

    private final ScenarioEventSender scenarioEventSender;

    private final UIKeyListener uiKeyListener;

    private final Map<Class<? extends HWEventPayload>, Consumer<? extends HWEventPayload>> consumers;

    public HWEventRouter(ScenarioEventSender scenarioEventSender, UIKeyListener uiKeyListener) {
        this.scenarioEventSender = scenarioEventSender;
        this.uiKeyListener = uiKeyListener;
        this.consumers = new HashMap<>();
        putClassConsumer(FuncKey.class, this::processFuncKey);
        putClassConsumer(Barcode.class, this::processBarcode);
        putClassConsumer(TypedKey.class, this::processTypedKey);
        putClassConsumer(MSRTracks.class, this::processMSRTracks);
        putClassConsumer(ControlKey.class, this::processControlKey);
    }

    public <T extends HWEventPayload> void processEvent(T event) {
        if (scenarioEventSender.isIgnoreCurrentEvents()) {
            System.out.println("Ignore event " + event);
        } else {
            Class<? extends HWEventPayload> aClass = event.getClass();
            Consumer<T> consumer = (Consumer<T>) consumers.get(aClass);
            consumer.accept(event);
        }
    }

    // send to UI

    private void processTypedKey(TypedKey typedKey) {
        uiKeyListener.onTypedKey(typedKey);
    }

    private void processControlKey(ControlKey controlKey) {
        uiKeyListener.onControlKey(controlKey);
    }

    // send to BL

    private void processFuncKey(FuncKey funcKey) {
        scenarioEventSender.onFunctionalKey(funcKey);
    }

    private void processBarcode(Barcode barcode) {
        scenarioEventSender.onBarcode(barcode.getCode());
    }

    private void processMSRTracks(MSRTracks msrTracks) {
        scenarioEventSender.onMSR(msrTracks);
    }

    ///

    private <T extends HWEventPayload> void putClassConsumer(Class<T> tClass, Consumer<T> consumer) {
        consumers.put(tClass, consumer);
    }

}


