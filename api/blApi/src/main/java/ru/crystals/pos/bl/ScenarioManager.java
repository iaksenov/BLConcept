package ru.crystals.pos.bl;

import ru.crystals.pos.bl.api.InOutScenario;
import ru.crystals.pos.bl.api.OutScenario;
import ru.crystals.pos.bl.api.Scenario;
import ru.crystals.pos.bl.api.SimpleScenario;
import ru.crystals.pos.bl.api.VoidListener;

import java.util.function.Consumer;

/**
 * Менеджер сценариев
 */
public interface ScenarioManager {

    /**
     * Запуск сценария по классу. Выполняется поиск единственной реализации в контексте.
     * @param scenarioClass класс сценария
     * @param <T>
     */
    <T extends SimpleScenario> void startScenario(Class<T> scenarioClass);

    void startScenario(SimpleScenario scenario);

    <O> void startScenario(OutScenario<O> scenario, Consumer<O> onComplete, VoidListener onCancel);

    <O> void startSubScenario(OutScenario<O> subScenario, Consumer<O> onComplete, VoidListener onCancel);

    <O> void startSubScenario(OutScenario<O> subScenario, VoidListener onComplete, VoidListener onCancel);

    <I, O> void startSubScenario(InOutScenario<I, O> subScenario, I arg, Consumer<O> onComplete, VoidListener onCancel);

    Scenario getCurrentScenario();

    Scenario getParentScenario(Scenario scenario);

    Scenario getSubScenario(Scenario parent);
}
