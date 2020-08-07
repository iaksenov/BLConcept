package ru.crystals.pos.ui.events;

/**
 * Статус кассы для UI
 */
public class POSStatusEvent {

    /**
     * Текущий кассир
     */
    private String currentCashierFIO;

    /**
     * Сервер онлайн
     */
    private boolean serverOnline;

    public String getCurrentCashierFIO() {
        return currentCashierFIO;
    }

    public void setCurrentCashierFIO(String currentCashierFIO) {
        this.currentCashierFIO = currentCashierFIO;
    }

    public boolean isServerOnline() {
        return serverOnline;
    }

    public void setServerOnline(boolean serverOnline) {
        this.serverOnline = serverOnline;
    }
}
