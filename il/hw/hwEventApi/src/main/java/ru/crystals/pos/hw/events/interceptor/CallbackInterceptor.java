package ru.crystals.pos.hw.events.interceptor;

import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import ru.crystals.pos.hw.events.UIHumanEvent;

import java.util.function.Consumer;
import java.util.function.Supplier;

@Service
public class CallbackInterceptor extends AbstractFactoryBean<CallbackInterceptor> implements ApplicationEventPublisherAware {

    private static CallbackInterceptor instance;
    private static Supplier<Boolean> isLockedFunc;

    private static ApplicationEventPublisher publisher;

    public static <C> void publishCallback(Consumer<C> callback, C c) {
        if (!isLocked()) {
            publisher.publishEvent(new UIHumanEvent<>(callback, c));
        }
    }

    public static void publichCallback(Runnable callback) {
        if (!isLocked()) {
            publisher.publishEvent(new UIHumanEvent<>(callback));
        }
    }

    private static boolean isLocked() {
        boolean isLocked = isLockedFunc == null || isLockedFunc.get() || publisher == null;
        if (isLocked) {
            System.out.println("BL locked, event ignored");
        }
        return isLocked;
    }

    public static void setLockedFunc(Supplier<Boolean> func) {
        isLockedFunc = func;
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

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        publisher = applicationEventPublisher;
    }

}
