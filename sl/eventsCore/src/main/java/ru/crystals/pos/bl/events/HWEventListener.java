package ru.crystals.pos.bl.events;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.crystals.pos.hw.events.HWHumanEvent;
import ru.crystals.pos.hw.events.HumanEvent;
import ru.crystals.pos.hw.events.UIHumanEvent;

import javax.annotation.PostConstruct;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Слушает human события.
 * Складывает в очередь.
 * Отправляет в HWEventRouter.
 */
@Service
public class HWEventListener {

    private final BlockingQueue<HumanEvent> eventQueue = new LinkedBlockingQueue<>();

    private final HWEventRouter processor;

    public HWEventListener(HWEventRouter processor) {
        this.processor = processor;
    }

    @PostConstruct
    private void postConstruct() {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.submit(this::loop);
    }

    @Async
    @EventListener
    public void onHwEvent(HumanEvent event) {
        eventQueue.add(event);
    }

    private void loop() {
        while (!Thread.currentThread().isInterrupted()) {
            try{
                HumanEvent event = eventQueue.take();
                if (event instanceof HWHumanEvent) {
                    processor.processEvent((HWHumanEvent) event);
                } else if (event instanceof UIHumanEvent) {
                    ((UIHumanEvent<?>)event).accept();
                }
            } catch (InterruptedException e) {
                break;
            } catch (Exception th){
                th.printStackTrace();
            }
        }
    }

}
