package ru.crystals.pos.bl.manager;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import ru.crystals.pos.bl.ScenarioManager;
import ru.crystals.pos.bl.api.InOutScenario;
import ru.crystals.pos.bl.api.LayerScenario;
import ru.crystals.pos.bl.api.OutScenario;
import ru.crystals.pos.bl.api.Scenario;
import ru.crystals.pos.bl.api.SimpleScenario;
import ru.crystals.pos.bl.api.VoidListener;
import ru.crystals.pos.ui.UI;
import ru.crystals.pos.ui.UILayer;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Component
public final class ScenarioManagerImpl implements ScenarioManager, ApplicationContextAware {

    private ApplicationContext applicationContext;

    private final UI ui;

    private UILayer currentLayer;

    private final Map<UILayer, ScenariosTree> layersTrees = new HashMap<>();

    public ScenarioManagerImpl(UI ui) {
        this.ui = ui;
    }

    UILayer getCurrentLayer() {
        return currentLayer;
    }

    void setCurrentLayer(UILayer currentLayer, LayerScenario layerScenario) {
        this.currentLayer = currentLayer;
        ui.setLayer(currentLayer);
        layersTrees.putIfAbsent(currentLayer, new ScenariosTree(layerScenario));
    }

    @Override
    public <T extends SimpleScenario> void startScenario(Class<T> scenarioClass) {
        try {
            T bean = applicationContext.getBean(scenarioClass);
            startScenario(bean);
        } catch (BeansException e) {
            e.printStackTrace();
        }
    }

    private ScenariosTree getTree() {
        return layersTrees.get(currentLayer);
    }

    @Override
    public void startScenario(SimpleScenario scenario) {
        if (scenario == null) {
            return;
        }
        getTree().replaceCurrent(scenario);
        scenario.start();
    }

    @Override
    public <O> void startScenario(OutScenario<O> scenario, Consumer<O> onComplete, VoidListener onCancel) {
        if (scenario == null) {
            return;
        }
        getTree().replaceCurrent(scenario);
        scenario.start(o -> onScenarioComplete(scenario, onComplete, o), () -> onScenarioCancel(scenario, onCancel));
    }

    @Override
    public <O> void startSubScenario(OutScenario<O> subScenario, Consumer<O> onComplete, VoidListener onCancel) {
        getTree().addChild(subScenario);
        startScenario(subScenario, c -> onScenarioComplete(subScenario, onComplete, c), () -> onScenarioCancel(subScenario, onCancel));
    }

    @Override
    public <O> void startSubScenario(OutScenario<O> subScenario, VoidListener onComplete, VoidListener onCancel) {
        getTree().addChild(subScenario);
        startScenario(subScenario, c -> onScenarioCancel(subScenario, onComplete), () -> onScenarioCancel(subScenario, onCancel));
    }

    @Override
    public <I, O> void startSubScenario(InOutScenario<I, O> subScenario, I arg, Consumer<O> onComplete, VoidListener onCancel) {
        if (subScenario == null) {
            return;
        }
        getTree().addChild(subScenario);
        subScenario.start(arg, o -> onScenarioComplete(subScenario, onComplete, o), () -> onScenarioCancel(subScenario, onCancel));
    }

    private <O> void onScenarioComplete(Scenario scenario, Consumer<O> onComplete, O o) {
        getTree().remove(scenario);
        onComplete.accept(o);
    }

    private void onScenarioCancel(Scenario scenario, VoidListener listener) {
        getTree().remove(scenario);
        listener.call();
    }

    @Override
    public Scenario getCurrentScenario() {
        return getTree().getCurrentScenario();
    }

    @Override
    public Scenario getParentScenario(Scenario subScenario) {
        return getTree().getParent(subScenario);
    }

    @Override
    public Scenario getSubScenario(Scenario parent) {
        return getTree().getChild(parent);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
