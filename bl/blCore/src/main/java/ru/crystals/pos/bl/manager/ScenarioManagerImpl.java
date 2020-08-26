package ru.crystals.pos.bl.manager;

import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

@Component
public final class ScenarioManagerImpl implements ScenarioManager {

    private static UI ui;
    private static UILayers uiLayers;
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private final ApplicationEventPublisher publisher;
    private final Map<UILayer, LayerScenario> layerScenario = new HashMap<>();

    private UILayer currentLayer;
    private final Map<UILayer, ScenariosTree> layersTrees = new HashMap<>();
    private final Set<LayerScenario> suspended = new HashSet<>();

    public static void setUi(UI ui, UILayers uiLayers) {
        ScenarioManagerImpl.ui = ui;
        ScenarioManagerImpl.uiLayers = uiLayers;
    }

    public ScenarioManagerImpl(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Autowired
    private void fillLayerScenarioList(List<LayerScenario> layerScenarioList) {
        for (LayerScenario layersScenario : layerScenarioList) {
            this.layerScenario.put(layersScenario.getLayer(), layersScenario);
        }
    }

    @Override
    public void setLayer(UILayer layer) {
        UILayer currentLayer = getCurrentLayer();
        if (currentLayer == layer) {
            return;
        }
        if (currentLayer != null) {
            suspendCurrent(this.layerScenario.get(currentLayer));
        }
        LayerScenario layerScenario = this.layerScenario.get(layer);
        setCurrentLayer(layer, layerScenario);
        if (isSuspended(layerScenario)) {
            suspended.remove(layerScenario);
            layerScenario.onResume();
        } else {
            startLayerScenario(layerScenario);
        }
    }


    @Override
    public UILayer getCurrentLayer() {
        return currentLayer;
    }

    @Override
    public <I> void start(InCompleteScenario<I> scenario, I arg, VoidListener onComplete) {
        getCurrentTree().replaceLast(scenario);
        scenario.start(createUIProxy(scenario), arg, () -> removeAndCall(scenario, onComplete));
    }

    @Override
    public <I, O> void start(InOutScenario<I, O> scenario, I arg, Consumer<O> onComplete) throws Exception {
        getCurrentTree().replaceLast(scenario);
        scenario.start(createUIProxy(scenario), arg, o -> removeAndAccept(scenario, onComplete, o));
    }

    @Override
    public <O> void start(OutCancelScenario<O> scenario, Consumer<O> onComplete, VoidListener onCancel) {
        getCurrentTree().replaceLast(scenario);
        scenario.start(createUIProxy(scenario), o -> removeAndAccept(scenario, onComplete, o), () -> removeAndCall(scenario, onCancel));
    }

    @Override
    public <I, O> void start(InOutCancelScenario<I, O> scenario, I arg, Consumer<O> onComplete, VoidListener onCancel) {
        getCurrentTree().replaceLast(scenario);
        scenario.start(createUIProxy(scenario), arg, o -> removeAndAccept(scenario, onComplete, o), () -> removeAndCall(scenario, onCancel));
    }

    /// child

    @Override
    public void startChild(CompleteScenario scenario, VoidListener onComplete) {
        getCurrentTree().addChild(scenario);
        scenario.start(createUIProxy(scenario), () -> removeAndCall(scenario, onComplete));
    }

    @Override
    public void startChild(CompleteCancelScenario scenario, VoidListener onComplete, VoidListener onCancel) {
        getCurrentTree().addChild(scenario);
        scenario.start(createUIProxy(scenario), () -> removeAndCall(scenario, onComplete), () -> removeAndCall(scenario, onCancel));
    }

    @Override
    public <I> void startChild(InScenario<I> scenario, I arg) {
        getCurrentTree().addChild(scenario);
        scenario.start(createUIProxy(scenario), arg);
    }

    @Override
    public <I> void startChild(InCompleteScenario<I> scenario, I arg, VoidListener onComplete) {
        getCurrentTree().addChild(scenario);
        scenario.start(createUIProxy(scenario), arg, () -> removeAndCall(scenario, onComplete));
    }

    @Override
    public <I> void startChild(InCompleteCancelScenario<I> scenario, I arg, VoidListener onComplete, VoidListener onCancel) {
        getCurrentTree().addChild(scenario);
        scenario.start(createUIProxy(scenario), arg, () -> removeAndCall(scenario, onComplete), () -> removeAndCall(scenario, onCancel));
    }

    @Override
    public <I, O> void startChild(InOutScenario<I, O> scenario, I arg, Consumer<O> onComplete) throws Exception {
        getCurrentTree().addChild(scenario);
        try {
            scenario.start(createUIProxy(scenario), arg, o -> removeAndAccept(scenario, onComplete, o));
        } catch (Exception e) {
            getCurrentTree().remove(scenario);
            throw e;
        }
    }

    @Override
    public <I, O> void startChildAsync(InOutScenario<I, O> scenario, I arg, Consumer<O> onComplete, Consumer<Exception> onError) {
        getCurrentTree().addChild(scenario);
        executorService.submit(() -> {
            try {
                scenario.start(createUIProxy(scenario), arg, o -> removeAndAccept(scenario, onComplete, o));
            } catch (Exception e) {
                e.printStackTrace();
                getCurrentTree().remove(scenario);
                onError.accept(e);
            }
        });
    }


    @Override
    public <O> void startChild(OutCancelScenario<O> scenario, Consumer<O> onComplete, VoidListener onCancel) {
        getCurrentTree().addChild(scenario);
        scenario.start(createUIProxy(scenario), o -> removeAndAccept(scenario, onComplete, o), () -> removeAndCall(scenario, onCancel));
    }

    @Override
    public <I, O> void startChild(InOutCancelScenario<I, O> scenario, I arg, Consumer<O> onComplete, VoidListener onCancel) {
        getCurrentTree().addChild(scenario);
        scenario.start(createUIProxy(scenario), arg, o -> removeAndAccept(scenario, onComplete, o), () -> removeAndCall(scenario, onCancel));
    }

    ///////// tryTo

    @Override
    public <C> void tryToComplete(Scenario scenario, Consumer<C> onComplete) throws ForceImpossibleException {
        if (scenario instanceof ForceCompletedScenario) {
            if (getCurrentTree().contains(scenario)) {
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
            if (getCurrentTree().contains(scenario)) {
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
            if (getCurrentTree().contains(scenario)) {
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
        return getCurrentTree().getLast();
    }

    @Override
    public Scenario getParentScenario(Scenario subScenario) {
        return getCurrentTree().getParent(subScenario);
    }

    @Override
    public Scenario getChildScenario(Scenario parent) {
        return getCurrentTree().getChild(parent);
    }

    @Override
    public boolean isActive(Scenario scenario) {
        return getCurrentTree().contains(scenario);
    }

    // private

    private void startLayerScenario(LayerScenario layerScenario) {
        layerScenario.start(createUIProxy(layerScenario));
    }

    private UI createUIProxy(Scenario scenario) {
        return new UIProxyImpl(scenario, ui, s -> getCurrentScenario() == s);
    }

    private <O> void removeAndAccept(Scenario scenario, Consumer<O> onComplete, O o) {
        getCurrentTree().remove(scenario);
        if (onComplete != null) {
            onComplete.accept(o);
        }
    }

    private void removeAndCall(Scenario scenario, VoidListener listener) {
        getCurrentTree().remove(scenario);
        if (listener != null) {
            listener.call();
        }
    }

    private void suspendCurrent(LayerScenario layerScenario) {
        layerScenario.onSuspend();
        suspended.add(layerScenario);
    }

    private boolean isSuspended(LayerScenario layerScenario) {
        return suspended.contains(layerScenario);
    }

    private synchronized void setCurrentLayer(UILayer currentLayer, LayerScenario layerScenario) {
        this.currentLayer = currentLayer;
        uiLayers.setLayer(currentLayer);
        layersTrees.putIfAbsent(currentLayer, new ScenariosTree(publisher, layerScenario));
        layersTrees.get(currentLayer).log();
    }

    private ScenariosTree getCurrentTree() {
        return layersTrees.get(currentLayer);
    }

}
