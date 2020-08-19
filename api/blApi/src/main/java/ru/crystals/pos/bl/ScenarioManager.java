package ru.crystals.pos.bl;

import ru.crystals.pos.bl.api.listener.VoidListener;
import ru.crystals.pos.bl.api.scenarios.*;
import ru.crystals.pos.bl.api.scenarios.special.ForceCompleteImpossibleException;

import java.util.function.Consumer;

/**
 * Менеджер сценариев
 */
public interface ScenarioManager {

    /// start

    <I> void start(InScenario<I> scenario, I arg);

    <O> void start(OutScenario<O> scenario, Consumer<O> onComplete);

    <I, O> void start(InOutScenario<I, O> scenario, I arg, Consumer<O> onComplete) throws Exception;

    <O> void start(OutCancelScenario<O> scenario, Consumer<O> onComplete, VoidListener onCancel);

    <I, O> void start(InOutCancelScenario<I, O> scenario, I arg, Consumer<O> onComplete, VoidListener onCancel);

    /// startChild

    void startChild(CompleteScenario scenario, VoidListener onComplete);

    void startChild(CompleteCancelScenario scenario, VoidListener onComplete,  VoidListener onCancel);

    <I> void startChild(InCompleteCancelScenario<I> scenario, I arg, VoidListener onComplete, VoidListener onCancel);

    <O> void startChild(OutScenario<O> scenario, Consumer<O> onComplete);

    <I, O> void startChild(InOutScenario<I, O> scenario, I arg, Consumer<O> onComplete) throws Exception;

    <I, O> void startChildAsync(InOutScenario<I, O> scenario, I arg, Consumer<O> onComplete, Consumer<Exception> onError);

    <O> void startChild(OutCancelScenario<O> scenario, Consumer<O> onComplete, VoidListener onCancel);

    <I, O> void startChild(InOutCancelScenario<I, O> scenario, I arg, Consumer<O> onComplete, VoidListener onCancel);

    /// tryTo

    <C> void tryToComplete(Scenario scenario, Consumer<C> onComplete) throws ForceCompleteImpossibleException;

    void tryToComplete(Scenario scenario, VoidListener listener) throws ForceCompleteImpossibleException;

    /// other

    Scenario getCurrentScenario();

    Scenario getParentScenario(Scenario scenario);

    Scenario getChildScenario(Scenario parent);

    boolean isActive(Scenario scenario);
}
