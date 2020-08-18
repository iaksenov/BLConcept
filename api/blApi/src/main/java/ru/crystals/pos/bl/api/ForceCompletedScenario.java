package ru.crystals.pos.bl.api;

import java.util.function.Consumer;

public interface ForceCompletedScenario<C> {

    boolean tryToComplete(Consumer<C> onComplete);

}
