package ru.crystals.pos.bl.api.login;

import ru.crystals.pos.bl.api.SimpleScenario;
import ru.crystals.pos.hw.events.listeners.BarcodeListener;
import ru.crystals.pos.hw.events.listeners.MSRListener;

public interface LoginScenario extends SimpleScenario, BarcodeListener, MSRListener {

}
