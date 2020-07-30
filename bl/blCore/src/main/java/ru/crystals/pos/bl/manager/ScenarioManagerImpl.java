package ru.crystals.pos.bl.manager;

import org.springframework.stereotype.Component;
import ru.crystals.pos.bl.ScenarioManager;
import ru.crystals.pos.bl.api.CompletedScenario;
import ru.crystals.pos.bl.api.Scenario;
import ru.crystals.pos.bl.api.SimpleScenario;
import ru.crystals.pos.bl.api.VoidListener;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Component
public final class ScenarioManagerImpl implements ScenarioManager {

    private Scenario currentScenario;

    // Map <child, parent>
    private final Map<Scenario, Scenario> subScenarios = new HashMap<>();
    // Map <parent, child>
    private final Map<Scenario, Scenario> parentScenarios = new HashMap<>();

    @Override
    public void startScenario(SimpleScenario scenario) {
        if (currentScenario != scenario && scenario != null) {
            this.currentScenario = scenario;
            scenario.start();
        }
    }

    @Override
    public <T> void startScenario(CompletedScenario<T> scenario, Consumer<T> onComplete, VoidListener onCancel) {
        if (currentScenario != scenario && scenario != null) {
            this.currentScenario = scenario;
            scenario.start(onComplete, onCancel);
        }
    }

    @Override
    public <T> void startScenario(CompletedScenario<T> scenario, VoidListener onComplete, VoidListener onCancel) {
        startScenario(scenario, c -> onComplete.call(), onCancel);
    }

    @Override
    public  <T> void startSubScenario(Scenario parent, CompletedScenario<T> subScenario, VoidListener onComplete, VoidListener onCancel) {
        subScenarios.put(subScenario, parent);
        parentScenarios.put(parent, subScenario);
        startScenario(subScenario, c -> onSubScenarioFinish(parent, subScenario, onComplete), () -> onSubScenarioFinish(parent, subScenario, onCancel));
    }

    private void onSubScenarioFinish(Scenario parent, Scenario subScenario, VoidListener listener) {
        subScenarios.remove(subScenario);
        parentScenarios.remove(parent);
        listener.call();
    }

    @Override
    public Scenario getCurrentScenario() {
        return currentScenario;
    }

    @Override
    public Scenario getParentScenario(Scenario subScenario) {
        return subScenarios.get(subScenario);
    }

    @Override
    public Scenario getSubScenario(Scenario parent) {
        return parentScenarios.get(parent);
    }

}
