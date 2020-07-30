package ru.crystals.pos.bl.events;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.crystals.pos.hw.events.HWEvent;

import javax.annotation.PostConstruct;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class HWEventListener {

    private final BlockingQueue<HWEvent> eventQueue = new LinkedBlockingQueue<>();

    private final HWEventProcessor processor;

    public HWEventListener(HWEventProcessor processor) {
        this.processor = processor;
    }

    @PostConstruct
    private void postConstruct() {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.submit(this::loop);
    }

    @Async
    @EventListener
    public void onHwEvent(HWEvent event) {
        eventQueue.add(event);
    }

    private void loop() {
        while (!Thread.currentThread().isInterrupted()) {
            try{
                HWEvent event = eventQueue.take();
                processor.processEvent(event.getPayload());
            } catch (InterruptedException e) {
                break;
            }catch (Exception th){
                th.printStackTrace();
            }
        }
    }

}
