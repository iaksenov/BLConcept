package ru.crystals.pos.hw.events.ru.crystals.pos.hw.interceptor;

import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Service
public class CallbackInterceptor extends AbstractFactoryBean<CallbackInterceptor> {

    private static final AtomicBoolean lock = new AtomicBoolean(false);

    private static CallbackInterceptor instance;
    private static Supplier<Boolean> checkIgnoreFunc;

    public static <C> void getConsumer(Consumer<C> callback, C c) {
        if (checkIgnoreFunc == null || checkIgnoreFunc.get()) {
            System.out.println("Callback ignored");
        } else {
            callback.accept(c);
        }
    }

    public static void setCheckIgnore(Supplier<Boolean> func) {
        checkIgnoreFunc = func;
    }

    public static void getRunnable(Runnable callback) {
        if (checkIgnoreFunc == null || checkIgnoreFunc.get()) {
            System.out.println("Callback ignored");
        } else {
            callback.run();
        }
    }

    public void lock() {
        lock.set(true);
    }

    @Override
    public Class<?> getObjectType() {
        return CallbackInterceptor.class;
    }

    @Override
    protected CallbackInterceptor createInstance() throws Exception {
        if (instance == null) {
            instance = new CallbackInterceptor();
        }
        return instance;
    }
}
