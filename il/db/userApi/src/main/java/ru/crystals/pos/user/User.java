package ru.crystals.pos.user;

import java.util.Collections;
import java.util.Set;

public class User {

    private String firstName;

    private String lastName;

    private String middleName;

    private Set<UserRight> rights = Collections.emptySet();

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public boolean hasRight(UserRight right) {
        return rights.contains(right);
    }

}
