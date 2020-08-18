package ru.crystals.pos.bl.api.scenarios;

/**
 * Интерфейс сценария с аргументом и без результата
 * @param <IN> тип аргумента
 */
public interface InScenario<IN> extends Scenario {

    void start(IN inArg);

}
