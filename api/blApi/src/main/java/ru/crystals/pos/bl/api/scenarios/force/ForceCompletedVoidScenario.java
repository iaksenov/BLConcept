package ru.crystals.pos.bl.api.scenarios.force;

/**
 * Опциональный интерфейс реализации сценариев для принудительного завершения
 *
 **/
public interface ForceCompletedVoidScenario {

    /**
     * Призыв завершиться
     * @throws ForceCompleteImpossibleException если не удалось, невозможно, недопустимо и пр.
     */
    void tryToComplete() throws ForceCompleteImpossibleException;
}
