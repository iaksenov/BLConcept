package ru.crystals.pos.bl;

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
import ru.crystals.pos.bl.api.scenarios.special.ForceImpossibleException;

import java.util.function.Consumer;

/**
 * Менеджер сценариев
 */
public interface ScenarioManager {

    /// start

    <I> void start(InCompleteScenario<I> scenario, I arg, VoidListener onComplete);

    <I, O> void start(InOutScenario<I, O> scenario, I arg, Consumer<O> onComplete) throws Exception;

    <O> void start(OutCancelScenario<O> scenario, Consumer<O> onComplete, VoidListener onCancel);

    <I, O> void start(InOutCancelScenario<I, O> scenario, I arg, Consumer<O> onComplete, VoidListener onCancel);

    /// startChild

    void startChild(CompleteScenario scenario, VoidListener onComplete);

    void startChild(CompleteCancelScenario scenario, VoidListener onComplete,  VoidListener onCancel);

    <I> void startChild(InScenario<I> scenario, I arg);

    <I> void startChild(InCompleteScenario<I> scenario, I arg, VoidListener onComplete);

    <I> void startChild(InCompleteCancelScenario<I> scenario, I arg, VoidListener onComplete, VoidListener onCancel);

    <I, O> void startChild(InOutScenario<I, O> scenario, I arg, Consumer<O> onComplete) throws Exception;

    <O> void startChild(OutCancelScenario<O> scenario, Consumer<O> onComplete, VoidListener onCancel);

    <I, O> void startChild(InOutCancelScenario<I, O> scenario, I arg, Consumer<O> onComplete, VoidListener onCancel);

    // async

    <I, O> void startChildAsync(InOutScenario<I, O> scenario, I arg, Consumer<O> onComplete, Consumer<Exception> onError);

    /// tryTo

    <C> void tryToComplete(Scenario scenario, Consumer<C> onComplete) throws ForceImpossibleException;

    void tryToComplete(Scenario scenario, VoidListener listener) throws ForceImpossibleException;

    void tryToCancel(Scenario scenario, VoidListener listener) throws ForceImpossibleException;

    /// other

    Scenario getCurrentScenario();

    Scenario getParentScenario(Scenario scenario);

    Scenario getChildScenario(Scenario parent);

    boolean isActive(Scenario scenario);

}
