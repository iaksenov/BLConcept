package ru.crystals.pos.bl.api.scenarios.special;

/**
 * Опциональный интерфейс реализации сценариев для принудительной отмены
 **/
public interface ForceCancelledScenario {

    /**
     * Призыв отмениться
     * @throws ForceImpossibleException если не удалось, невозможно, недопустимо и пр.
     */
    void tryToCancel() throws ForceImpossibleException;

}
