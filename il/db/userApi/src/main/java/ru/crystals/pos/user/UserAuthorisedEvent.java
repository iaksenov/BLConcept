package ru.crystals.pos.user;

import ru.crystals.pos.hw.events.BaseEvent;

public class UserAuthorisedEvent extends BaseEvent<User> {

    private User user;

    public UserAuthorisedEvent(Object source, User user) {
        super(source, user);
    }

}
