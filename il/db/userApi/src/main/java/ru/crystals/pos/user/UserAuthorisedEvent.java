package ru.crystals.pos.user;

public class UserAuthorisedEvent {

    private User user;

    public UserAuthorisedEvent(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
