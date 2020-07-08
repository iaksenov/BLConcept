package ru.crystals.pos.bl;

import ru.crystals.pos.bl.api.VoidListener;
import ru.crystals.pos.bl.api.CompletedScenario;
import ru.crystals.pos.bl.api.Scenario;
import ru.crystals.pos.bl.api.SimpleScenario;

import java.util.function.Consumer;

public interface ScenarioManager {

    void startScenario(SimpleScenario scenario);

    <T> void startScenario(CompletedScenario<T> scenario, Consumer<T> onComplete, VoidListener onCancel);

    <T> void startScenario(CompletedScenario<T> scenario, VoidListener onComplete, VoidListener onCancel);

    <T> void startSubScenario(Scenario parent, CompletedScenario<T> subScenario, VoidListener onComplete, VoidListener onCancel);

    Scenario getCurrentScenario();

    Scenario getParentScenario(Scenario scenario);

    Scenario getSubScenario(Scenario parent);
}
