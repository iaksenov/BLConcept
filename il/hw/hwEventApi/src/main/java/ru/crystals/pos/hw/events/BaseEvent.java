package ru.crystals.pos.hw.events;

import org.springframework.context.ApplicationEvent;

/**
 * Базовое событие для отправки спрингом.
 * @param <T>
 */
public class BaseEvent<T> extends ApplicationEvent {

    private final T payload;

    public BaseEvent(Object source, T payload) {
        super(source);
        this.payload = payload;
    }

    public T getPayload() {
        return payload;
    }

}
