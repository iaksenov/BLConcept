package ru.crystals.pos.bl;

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
import ru.crystals.pos.bl.api.scenarios.special.ForceImpossibleException;
import ru.crystals.pos.ui.UILayer;

import java.util.function.Consumer;

/**
 * Интерфейс менеджера сценариев.
 * Содержит методы для передачи управления сценариям и управления текущим слоем.
 */
public interface ScenarioManager {

    /// layer methods

    /**
     * Сменить текущий слой. Управление будет передано сценарию указанного слоя. <p>
     * В текущем сценарии слоя будет вызван метод {@link LayerScenario#onSuspend()} <p>
     * Если активируемый сценарий слоя был ранее приостановлен, то у него будет вызван метод {@link LayerScenario#onResume()}  <p>
     *
     * @see ru.crystals.pos.bl.api.layer.LayerScenario
     * @see UILayer
     * @param layer слой
     */
    void setLayer(UILayer layer);

    /**
     * Получить текущий слой.
     *
     * @return слой.
     */
    UILayer getCurrentLayer();


    /// start

    /**
     * Передача управления сценарию вместо текущего последнего.
     *
     * @param scenario сценарий
     * @param arg аргумент
     * @param onComplete при завершении
     * @param <I> тип аргумента
     */
    <I> void start(InCompleteScenario<I> scenario, I arg, VoidListener onComplete);

    /**
     * Передача управления сценарию вместо текущего последнего.
     *
     * @param scenario сценарий
     * @param arg аргумент
     * @param onComplete при завершении
     * @param <I> тип аргумента
     * @param <O> тип результата
     * @throws Exception ошибки
     */
    <I, O> void start(InOutScenario<I, O> scenario, I arg, Consumer<O> onComplete) throws Exception;

    /**
     * Передача управления сценарию вместо текущего последнего.
     *
     * @param scenario сценарий
     * @param onComplete при завершении
     * @param onCancel при отмене
     * @param <O> тип результата
     */
    <O> void start(OutCancelScenario<O> scenario, Consumer<O> onComplete, VoidListener onCancel);

    /**
     * Передача управления сценарию вместо текущего последнего.
     *
     * @param scenario сценарий
     * @param arg аргумент
     * @param onComplete при завершении
     * @param onCancel при отмене
     * @param <I> тип аргумента
     * @param <O> тип результата
     */
    <I, O> void start(InOutCancelScenario<I, O> scenario, I arg, Consumer<O> onComplete, VoidListener onCancel);

    /// startChild

    /**
     * Добавление сценария после текущего последнего.
     * После завершения, он перестанет быть последним.
     *
     * @param scenario сценарий
     * @param onComplete при завершении
     */
    void startChild(CompleteScenario scenario, VoidListener onComplete);

    /**
     * Добавление сценария после текущего последнего.
     * После завершения, он перестанет быть последним.
     *
     * @param scenario сценарий
     * @param onComplete при завершении
     * @param onCancel при отмене
     */
    void startChild(CompleteCancelScenario scenario, VoidListener onComplete,  VoidListener onCancel);

    /**
     * Добавление сценария после текущего последнего.
     * После завершения, он перестанет быть последним.
     * Завершить этот тип сценария можно только методами tryToComplete.
     *
     * @param scenario сценарий
     * @param arg аргумент
     * @param <I> тип аргумента
     */
    <I> void startChild(InScenario<I> scenario, I arg);

    /**
     * Добавление сценария после текущего последнего.
     * После завершения, он перестанет быть последним.
     *
     * @param scenario сценарий
     * @param arg аргумент
     * @param onComplete при завершении
     * @param <I> тип аргумента
     */
    <I> void startChild(InCompleteScenario<I> scenario, I arg, VoidListener onComplete);

    /**+
     * Добавление сценария после текущего последнего.
     * После завершения, он перестанет быть последним.
     *
     * @param scenario сценарий
     * @param arg аргумент
     * @param onComplete при завершении
     * @param onCancel при отмене
     * @param <I> тип аргумента
     */
    <I> void startChild(InCompleteCancelScenario<I> scenario, I arg, VoidListener onComplete, VoidListener onCancel);

    /**
     * Добавление сценария после текущего последнего.
     * После завершения, он перестанет быть последним.
     *
     * @param scenario сценарий
     * @param arg аргумент
     * @param onComplete при завершении
     * @param <I> тип аргумента
     * @param <O> тип результата
     * @throws Exception ошибки
     */
    <I, O> void startChild(InOutScenario<I, O> scenario, I arg, Consumer<O> onComplete) throws Exception;

    /**
     * Добавление сценария после текущего последнего.
     * После завершения, он перестанет быть последним.
     *
     * @param scenario сценарий
     * @param onComplete при завершении
     * @param onCancel при отмене
     * @param <O> тип результата
     */
    <O> void startChild(OutCancelScenario<O> scenario, Consumer<O> onComplete, VoidListener onCancel);

    /**
     * Добавление сценария после текущего последнего.
     * После завершения, он перестанет быть последним.
     *
     * @param scenario сценарий
     * @param arg аргумент
     * @param onComplete при завершении
     * @param onCancel при отмене
     * @param <I> тип аргумента
     * @param <O> тип результата
     */
    <I, O> void startChild(InOutCancelScenario<I, O> scenario, I arg, Consumer<O> onComplete, VoidListener onCancel);

    // Async methods
    // TODO: добавить методы для остальных типов

    /**
     * Добавление сценария после текущего последнего и запуск в отдельном потоке.
     * После завершения, он перестанет быть последним.
     *
     * @param scenario сценарий
     * @param arg аргумент
     * @param onComplete при завершении
     * @param onError при ошибках
     * @param <I> тип аргумента
     * @param <O> тип результата
     */
    <I, O> void startChildAsync(InOutScenario<I, O> scenario, I arg, Consumer<O> onComplete, Consumer<Exception> onError);

    /// TryTo methods
    // TODO: добавить методы для остальных типов

    /**
     * Попросить сценарий завершиться.
     *
     * @param scenario сценарий, наследник {@link ru.crystals.pos.bl.api.scenarios.special.ForceCompletedScenario}
     * @param onComplete при завершении
     * @param <C> тип результата
     * @throws ForceImpossibleException ошибки
     */
    <C> void tryToComplete(Scenario scenario, Consumer<C> onComplete) throws ForceImpossibleException;

    /**
     * Попросить сценарий завершиться.
     *
     * @param scenario сценарий, наследник {@link ru.crystals.pos.bl.api.scenarios.special.ForceCompletedVoidScenario}
     * @param listener при завершении
     * @throws ForceImpossibleException ошибки
     */
    void tryToComplete(Scenario scenario, VoidListener listener) throws ForceImpossibleException;

    /**
     * Попросить сценарий отмениться.
     *
     * @param scenario сценарий, наследник {@link ru.crystals.pos.bl.api.scenarios.special.ForceCancelledScenario}
     * @param listener при успехе отмены
     * @throws ForceImpossibleException ошибки
     */
    void tryToCancel(Scenario scenario, VoidListener listener) throws ForceImpossibleException;

    /// other

    /**
     * Получить текущий (последний) сценарий в текущем слое.
     *
     * @return сценарий
     */
    Scenario getCurrentScenario();

    /**
     * Получить предыдущий относительно указанного сценарий в текущем слое.
     *
     * @param scenario сценарий
     * @return сценарий
     */
    Scenario getParentScenario(Scenario scenario);

    /**
     * Получить следующий относительно указанного сценарий в текущем слое.
     *
     * @param parent сценарий
     * @return сценарий
     */
    Scenario getChildScenario(Scenario parent);

    /**
     * Проверка на активность сценария в текущем слое.<p>
     * Будет проверяется наличие сценария во всей цепочке.
     *
     * @param scenario сценарий
     * @return true - присутствует, false - отсутствует
     */
    boolean isActive(Scenario scenario);

}
