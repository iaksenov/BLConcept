package ru.crystals.pos.bl.api.scenarios.special;

/**
 * Опциональный интерфейс реализации сценариев для принудительного завершения
 *
 **/
public interface ForceCompletedVoidScenario {

    /**
     * Призыв завершиться
     * @throws ForceImpossibleException если не удалось, невозможно, недопустимо и пр.
     */
    void tryToComplete() throws ForceImpossibleException;
}
