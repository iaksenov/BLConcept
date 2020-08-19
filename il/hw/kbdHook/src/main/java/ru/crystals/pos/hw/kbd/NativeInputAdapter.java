package ru.crystals.pos.hw.kbd;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.NativeInputEvent;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import ru.crystals.pos.hw.events.HWHumanEvent;
import ru.crystals.pos.hw.events.keys.ControlKey;
import ru.crystals.pos.hw.events.keys.ControlKeyType;
import ru.crystals.pos.hw.events.keys.TypedKey;
import ru.crystals.pos.hw.events.listeners.Barcode;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Перехватчик native событий от клавиатуры
 */
@Service
public class NativeInputAdapter implements NativeKeyListener {

    private static final int BRACKETRIGHT_RAWCODE = 93;
    private static final List<Integer> excludeWithCtrlList = Collections.singletonList(BRACKETRIGHT_RAWCODE);

    private final CopyOnWriteArrayList<NativeKeyEvent> listeners;

    private Map<String, HWHumanEvent> keysMap = new HashMap<>();

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    public NativeInputAdapter() throws NativeHookException {
        this.listeners = null;
        initKeysMap();
        GlobalScreen.registerNativeHook();
        GlobalScreen.addNativeKeyListener(this);
    }

    private void initKeysMap() {
        keysMap.put("Enter", new ControlKey(ControlKeyType.ENTER));
        keysMap.put("Up", new ControlKey(ControlKeyType.UP));
        keysMap.put("Down", new ControlKey(ControlKeyType.DOWN));
        keysMap.put("Left", new ControlKey(ControlKeyType.LEFT));
        keysMap.put("Right", new ControlKey(ControlKeyType.RIGHT));
        keysMap.put("Escape", new ControlKey(ControlKeyType.ESC));
        keysMap.put("Backspace", new ControlKey(ControlKeyType.BACKSPACE));

        keysMap.put("F12", new Barcode("XXXXX"));
        keysMap.put("F5", new Barcode("12345"));
        keysMap.put("F2", new Barcode("X-002"));
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
        if (isExcludedWithCtrl(nativeKeyEvent)) {
            return;
        }
        String keyText = nativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode());

        if ("F8".equals(keyText)) {
            eventPublisher.publishEvent(new TypedKey('8'));
            eventPublisher.publishEvent(new Barcode("00000008"));
            return;
        }

        HWHumanEvent hwEvent = keysMap.get(keyText);
        if (hwEvent != null) {
            eventPublisher.publishEvent(hwEvent);
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {
        if (isExcludedWithCtrl(nativeKeyEvent)) {
            return;
        }
        //ignore
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {
        if (isExcludedWithCtrl(nativeKeyEvent)) {
            return;
        }
        eventPublisher.publishEvent(new TypedKey(nativeKeyEvent.getKeyChar()));
    }

    private static boolean isExcludedWithCtrl(NativeKeyEvent nativeKeyEvent) {
        return (nativeKeyEvent.getModifiers() & NativeInputEvent.CTRL_MASK) != 0 && excludeWithCtrlList.contains(nativeKeyEvent.getRawCode());
    }

}
