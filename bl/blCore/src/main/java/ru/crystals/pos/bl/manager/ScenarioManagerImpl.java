package ru.crystals.pos.bl.manager;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import ru.crystals.pos.bl.ScenarioManager;
import ru.crystals.pos.bl.api.layer.LayerScenario;
import ru.crystals.pos.bl.api.listener.VoidListener;
import ru.crystals.pos.bl.api.scenarios.CompleteCancelScenario;
import ru.crystals.pos.bl.api.scenarios.CompleteScenario;
import ru.crystals.pos.bl.api.scenarios.InCompleteCancelScenario;
import ru.crystals.pos.bl.api.scenarios.InCompleteScenario;
import ru.crystals.pos.bl.api.scenarios.InOutCancelScenario;
import ru.crystals.pos.bl.api.scenarios.InOutScenario;
import ru.crystals.pos.bl.api.scenarios.InScenario;
import ru.crystals.pos.bl.api.scenarios.OutCancelScenario;
import ru.crystals.pos.bl.api.scenarios.Scenario;
import ru.crystals.pos.bl.api.scenarios.special.ForceCancelledScenario;
import ru.crystals.pos.bl.api.scenarios.special.ForceCompletedScenario;
import ru.crystals.pos.bl.api.scenarios.special.ForceCompletedVoidScenario;
import ru.crystals.pos.bl.api.scenarios.special.ForceImpossibleException;
import ru.crystals.pos.ui.UI;
import ru.crystals.pos.ui.UILayer;
import ru.crystals.pos.ui.UILayers;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

@Component
public final class ScenarioManagerImpl implements ScenarioManager {

    private static UI ui;
    private static UILayers uiLayers;
    private ExecutorService executorService = Executors.newCachedThreadPool();

    private final ApplicationEventPublisher publisher;

    private UILayer currentLayer;

    private Map<UILayer, ScenariosTree> layersTrees = new HashMap<>();

    public ScenarioManagerImpl(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public static void setUi(UI ui, UILayers uiLayers) {
        ScenarioManagerImpl.ui = ui;
        ScenarioManagerImpl.uiLayers = uiLayers;
    }

    UILayer getCurrentLayer() {
        return currentLayer;
    }

    void setCurrentLayer(UILayer currentLayer, LayerScenario layerScenario) {
        this.currentLayer = currentLayer;
        uiLayers.setLayer(currentLayer);
        layersTrees.putIfAbsent(currentLayer, new ScenariosTree(publisher, layerScenario));
        layersTrees.get(currentLayer).log();
    }

    private ScenariosTree getTree() {
        return layersTrees.get(currentLayer);
    }

    public void start(LayerScenario layerScenario) {
        layerScenario.start(createUIProxy(layerScenario));
    }

    private UI createUIProxy(Scenario scenario) {
        return new UIProxyImpl(scenario, ui, s -> getCurrentScenario() == s);
    }

    @Override
    public <I> void start(InCompleteScenario<I> scenario, I arg, VoidListener onComplete) {
        getTree().replaceLast(scenario);
        scenario.start(createUIProxy(scenario), arg, () -> removeAndCall(scenario, onComplete));
    }

    public <I, O> void start(InOutScenario<I, O> scenario, I arg, Consumer<O> onComplete) throws Exception {
        getTree().replaceLast(scenario);
        scenario.start(createUIProxy(scenario), arg, o -> removeAndAccept(scenario, onComplete, o));
    }

    public <O> void start(OutCancelScenario<O> scenario, Consumer<O> onComplete, VoidListener onCancel) {
        getTree().replaceLast(scenario);
        scenario.start(createUIProxy(scenario), o -> removeAndAccept(scenario, onComplete, o), () -> removeAndCall(scenario, onCancel));
    }

    public <I, O> void start(InOutCancelScenario<I, O> scenario, I arg, Consumer<O> onComplete, VoidListener onCancel) {
        getTree().replaceLast(scenario);
        scenario.start(createUIProxy(scenario), arg, o -> removeAndAccept(scenario, onComplete, o), () -> removeAndCall(scenario, onCancel));
    }

    /// child

    @Override
    public void startChild(CompleteScenario scenario, VoidListener onComplete) {
        getTree().addChild(scenario);
        scenario.start(createUIProxy(scenario), () -> removeAndCall(scenario, onComplete));
    }

    @Override
    public void startChild(CompleteCancelScenario scenario, VoidListener onComplete, VoidListener onCancel) {
        getTree().addChild(scenario);
        scenario.start(createUIProxy(scenario), () -> removeAndCall(scenario, onComplete), () -> removeAndCall(scenario, onCancel));
    }

    @Override
    public <I> void startChild(InScenario<I> scenario, I arg) {
        getTree().addChild(scenario);
        scenario.start(createUIProxy(scenario), arg);
    }

    @Override
    public <I> void startChild(InCompleteScenario<I> scenario, I arg, VoidListener onComplete) {
        getTree().addChild(scenario);
        scenario.start(createUIProxy(scenario), arg, () -> removeAndCall(scenario, onComplete));
    }

    @Override
    public <I> void startChild(InCompleteCancelScenario<I> scenario, I arg, VoidListener onComplete, VoidListener onCancel) {
        getTree().addChild(scenario);
        scenario.start(createUIProxy(scenario), arg, () -> removeAndCall(scenario, onComplete), () -> removeAndCall(scenario, onCancel));
    }

    public <I, O> void startChild(InOutScenario<I, O> scenario, I arg, Consumer<O> onComplete) throws Exception {
        getTree().addChild(scenario);
        try {
            scenario.start(createUIProxy(scenario), arg, o -> removeAndAccept(scenario, onComplete, o));
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
                scenario.start(createUIProxy(scenario), arg, o -> removeAndAccept(scenario, onComplete, o));
            } catch (Exception e) {
                e.printStackTrace();
                getTree().remove(scenario);
                onError.accept(e);
            }
        });
    }


    public <O> void startChild(OutCancelScenario<O> scenario, Consumer<O> onComplete, VoidListener onCancel) {
        getTree().addChild(scenario);
        scenario.start(createUIProxy(scenario), o -> removeAndAccept(scenario, onComplete, o), () -> removeAndCall(scenario, onCancel));
    }

    public <I, O> void startChild(InOutCancelScenario<I, O> scenario, I arg, Consumer<O> onComplete, VoidListener onCancel) {
        getTree().addChild(scenario);
        scenario.start(createUIProxy(scenario), arg, o -> removeAndAccept(scenario, onComplete, o), () -> removeAndCall(scenario, onCancel));
    }

    ///////// tryTo

    @Override
    public <C> void tryToComplete(Scenario scenario, Consumer<C> onComplete) throws ForceImpossibleException {
        if (scenario instanceof ForceCompletedScenario) {
            if (getTree().contains(scenario)) {
                ForceCompletedScenario<C> castedScenario = (ForceCompletedScenario<C>) scenario;
                try {
                    C c = castedScenario.tryToComplete();
                    removeAndAccept(scenario, onComplete, c);
                } catch (ForceImpossibleException e) {
                    System.out.println("Can't force complete " + scenario.getClass().getName() + ". " + e.getLocalizedMessage());
                    throw e;
                }
            } else {
                System.out.println("Scenario " + scenario.getClass().getName() + " is not active");
            }
        } else {
            System.out.println("Scenario " + scenario.getClass().getName() + " is not ForceCompletedScenario");
        }
    }

    @Override
    public void tryToComplete(Scenario scenario, VoidListener listener) throws ForceImpossibleException {
        if (scenario instanceof ForceCompletedVoidScenario) {
            if (getTree().contains(scenario)) {
                ForceCompletedVoidScenario castedScenario = (ForceCompletedVoidScenario) scenario;
                try {
                    castedScenario.tryToComplete();
                    removeAndCall(scenario, listener);
                } catch (ForceImpossibleException e) {
                    System.out.println("Can't force complete " + scenario.getClass().getName() + ". " + e.getLocalizedMessage());
                    throw e;
                }
            } else {
                System.out.println("Scenario " + scenario.getClass().getName() + " is not active");
            }
        } else {
            System.out.println("Scenario " + scenario.getClass().getName() + " is not ForceCompletedScenario");
        }
    }

    @Override
    public void tryToCancel(Scenario scenario, VoidListener listener) throws ForceImpossibleException {
        if (scenario instanceof ForceCancelledScenario) {
            if (getTree().contains(scenario)) {
                ForceCancelledScenario castedScenario = (ForceCancelledScenario) scenario;
                try {
                    castedScenario.tryToCancel();
                    removeAndCall(scenario, listener);
                } catch (ForceImpossibleException e) {
                    System.out.println("Can't force complete " + scenario.getClass().getName() + ". " + e.getLocalizedMessage());
                    throw e;
                }
            } else {
                System.out.println("Scenario " + scenario.getClass().getName() + " is not active");
            }
        } else {
            System.out.println("Scenario " + scenario.getClass().getName() + " is not ForceCompletedScenario");
        }
    }

    ///////// other

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

    // private

    private <O> void removeAndAccept(Scenario scenario, Consumer<O> onComplete, O o) {
        getTree().remove(scenario);
        if (onComplete != null) {
            onComplete.accept(o);
        }
    }

    private void removeAndCall(Scenario scenario, VoidListener listener) {
        getTree().remove(scenario);
        if (listener != null) {
            listener.call();
        }
    }

}
