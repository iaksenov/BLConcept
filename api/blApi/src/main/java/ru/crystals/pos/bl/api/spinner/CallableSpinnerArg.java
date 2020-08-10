package ru.crystals.pos.bl.api.spinner;

import java.util.concurrent.Callable;

public class CallableSpinnerArg<O> {

    private String message;

    private Callable<O> callable;

    public CallableSpinnerArg(String message, Callable<O> callable) {
        this.message = message;
        this.callable = callable;
    }

    public String getMessage() {
        return message;
    }

    public Callable<O> getCallable() {
        return callable;
    }
}
