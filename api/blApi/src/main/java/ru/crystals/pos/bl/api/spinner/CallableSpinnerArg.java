package ru.crystals.pos.bl.api.spinner;

import java.util.concurrent.Callable;

/**
 * Аргумент для {@link CallableSpinnerScenario}
 * @param <O> тип результата
 */
public class CallableSpinnerArg<O> {

    private String message;

    private Callable<O> callable;

    /**
     * Конструктор.
     * @param message текст сообщения во время выполнения
     * @param callable долгий процесс с результатом
     */
    public CallableSpinnerArg(String message, Callable<O> callable) {
        this.message = message;
        this.callable = callable;
    }

    /**
     * Получить текст сообщения во время выполнения
     * @return текст
     */
    public String getMessage() {
        return message;
    }

    /**
     * Получить долгий процесс с результатом
     * @return процесс
     */
    public Callable<O> getCallable() {
        return callable;
    }
}
