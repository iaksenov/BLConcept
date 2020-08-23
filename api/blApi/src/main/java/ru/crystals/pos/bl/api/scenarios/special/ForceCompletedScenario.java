package ru.crystals.pos.bl.api.scenarios.special;

/**
 * Опциональный интерфейс реализации сценариев для принудительного завершения
 * @param <C> тип возвращаемого значения при успехе
 */
public interface ForceCompletedScenario<C> {

    /**
     * Призыв завершиться
     * @return результат завершения
     * @throws ForceImpossibleException если не удалось, невозможно, недопустимо и пр.
     */
    C tryToComplete() throws ForceImpossibleException;

}
