package ru.crystals.pos.bl.manager;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import ru.crystals.pos.bl.ScenarioManager;
import ru.crystals.pos.bl.api.CompleteCancelScenario;
import ru.crystals.pos.bl.api.CompleteScenario;
import ru.crystals.pos.bl.api.ForceCompletedScenario;
import ru.crystals.pos.bl.api.InCompleteCancelScenario;
import ru.crystals.pos.bl.api.InOutCancelScenario;
import ru.crystals.pos.bl.api.InOutScenario;
import ru.crystals.pos.bl.api.InScenario;
import ru.crystals.pos.bl.api.OutCancelScenario;
import ru.crystals.pos.bl.api.OutScenario;
import ru.crystals.pos.bl.api.Scenario;
import ru.crystals.pos.bl.api.layer.LayerScenario;
import ru.crystals.pos.bl.api.listener.VoidListener;
import ru.crystals.pos.ui.UI;
import ru.crystals.pos.ui.UILayer;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

@Component
public final class ScenarioManagerImpl implements ScenarioManager, ApplicationContextAware {

    private ApplicationContext applicationContext;

    private final UI ui;

    private UILayer currentLayer;

    private final Map<UILayer, ScenariosTree> layersTrees = new HashMap<>();
    private final ExecutorService executorService;

    public ScenarioManagerImpl(UI ui) {
        this.ui = ui;
        executorService = Executors.newCachedThreadPool();
    }

    UILayer getCurrentLayer() {
        return currentLayer;
    }

    void setCurrentLayer(UILayer currentLayer, LayerScenario layerScenario) {
        this.currentLayer = currentLayer;
        ui.setLayer(currentLayer);
        layersTrees.putIfAbsent(currentLayer, new ScenariosTree(layerScenario));
    }

    private ScenariosTree getTree() {
        return layersTrees.get(currentLayer);
    }

    public <I> void start(InScenario<I> scenario, I arg) {
        getTree().replaceLast(scenario);
        scenario.start(arg);
    }

    public <O> void start(OutScenario<O> scenario, Consumer<O> onComplete) {
        getTree().replaceLast(scenario);
        scenario.start(o -> removeAndAccept(scenario, onComplete, o));
    }

    public <I, O> void start(InOutScenario<I, O> scenario, I arg, Consumer<O> onComplete) throws Exception {
        getTree().replaceLast(scenario);
        scenario.start(arg, o -> removeAndAccept(scenario, onComplete, o));
    }

    public <O> void start(OutCancelScenario<O> scenario, Consumer<O> onComplete, VoidListener onCancel) {
        getTree().replaceLast(scenario);
        scenario.start(o -> removeAndAccept(scenario, onComplete, o), () -> removeAndCall(scenario, onCancel));
    }

    public <I, O> void start(InOutCancelScenario<I, O> scenario, I arg, Consumer<O> onComplete, VoidListener onCancel) {
        getTree().replaceLast(scenario);
        scenario.start(arg, o -> removeAndAccept(scenario, onComplete, o), () -> removeAndCall(scenario, onCancel));
    }

    /// child

    @Override
    public void startChild(CompleteScenario scenario, VoidListener onComplete) {
        getTree().addChild(scenario);
        scenario.start(() -> removeAndCall(scenario, onComplete));
    }

    @Override
    public void startChild(CompleteCancelScenario scenario, VoidListener onComplete, VoidListener onCancel) {
        getTree().addChild(scenario);
        scenario.start(() -> removeAndCall(scenario, onComplete), () -> removeAndCall(scenario, onCancel));
    }

    @Override
    public <I> void startChild(InCompleteCancelScenario<I> scenario, I arg, VoidListener onComplete, VoidListener onCancel) {
        getTree().addChild(scenario);
        scenario.start(arg, () -> removeAndCall(scenario, onComplete), () -> removeAndCall(scenario, onCancel));
    }

    public <O> void startChild(OutScenario<O> scenario, Consumer<O> onComplete) {
        getTree().addChild(scenario);
        scenario.start(o -> removeAndAccept(scenario, onComplete, o));
    }

    public <I, O> void startChild(InOutScenario<I, O> scenario, I arg, Consumer<O> onComplete) throws Exception {
        getTree().addChild(scenario);
        try {
            scenario.start(arg, o -> removeAndAccept(scenario, onComplete, o));
        } catch (Exception e) {
            getTree().remove(scenario);
            throw e;
        }
    }

    @Override
    public <I, O> void startChildAsync(InOutScenario<I, O> scenario, I arg, Consumer<O> onComplete, Consumer<Exception> onError) {
        getTree().addChild(scenario);
        executorService.submit(() -> {
            try {
                scenario.start(arg, o -> removeAndAccept(scenario, onComplete, o));
            } catch (Exception e) {
                getTree().remove(scenario);
                onError.accept(e);
            }
        });
    }


    public <O> void startChild(OutCancelScenario<O> scenario, Consumer<O> onComplete, VoidListener onCancel) {
        getTree().addChild(scenario);
        scenario.start(o -> removeAndAccept(scenario, onComplete, o), () -> removeAndCall(scenario, onCancel));
    }

    public <I, O> void startChild(InOutCancelScenario<I, O> scenario, I arg, Consumer<O> onComplete, VoidListener onCancel) {
        getTree().addChild(scenario);
        scenario.start(arg, o -> removeAndAccept(scenario, onComplete, o), () -> removeAndCall(scenario, onCancel));
    }

    @Override
    public <C> boolean tryToComplete(Scenario scenario, Consumer<C> onComplete) {
        if (scenario instanceof ForceCompletedScenario) {
            if (getTree().contains(scenario)) {
                return ((ForceCompletedScenario<C>) scenario).tryToComplete(o -> removeAndAccept(scenario, onComplete, o));
            } else {
                System.out.println("Scenario " + scenario.getClass().getName() + " is not active");
                return false;
            }
        } else {
            System.out.println("Scenario " + scenario.getClass().getName() + " is not ForceCompletedScenario");
            return false;
        }
    }

    @Override
    public Scenario getCurrentScenario() {
        return getTree().getLast();
    }

    @Override
    public Scenario getParentScenario(Scenario subScenario) {
        return getTree().getParent(subScenario);
    }

    @Override
    public Scenario getChildScenario(Scenario parent) {
        return getTree().getChild(parent);
    }

    @Override
    public boolean isActive(Scenario scenario) {
        return getTree().contains(scenario);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    // private

    private <O> void removeAndAccept(Scenario scenario, Consumer<O> onComplete, O o) {
        getTree().remove(scenario);
        onComplete.accept(o);
    }

    private void removeAndCall(Scenario scenario, VoidListener listener) {
        getTree().remove(scenario);
        listener.call();
    }
}
