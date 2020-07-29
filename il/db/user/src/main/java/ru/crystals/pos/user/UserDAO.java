package ru.crystals.pos.user;

import org.springframework.stereotype.Service;
import ru.crystals.pos.hw.events.listeners.MSRTracks;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserDAO {

    private final Stream<DBUser> users;

    public UserDAO() {
        users = Stream.of(
            getUser("Кассир", "К", "К", "2", "X-002", "X-002", UserRight.SALE),
            getUser("Ст.кассир", "С", "С", "1", "X-001", "X-001", UserRight.SHIFT),
            getUser("Инженер", "И", "И", "3", "X-003", "X-003", UserRight.CONFIGURATION)
        );
    }

    private DBUser getUser(String firstName, String lastName, String middleName, String pwd, String barcode, String msr, UserRight... rights) {
        return new DBUser(firstName, lastName, middleName, Stream.of(rights).collect(Collectors.toSet()), pwd, barcode, msr);
    }

    public Optional<DBUser> getUserByPassword(String password) {
        return users.filter(u -> password.equals(u.getPwd())).findFirst();
    }


    public Optional<DBUser> getUserByBarcode(String code) {
        return users.filter(u -> code.equals(u.getBarcode())).findFirst();
    }

    public Optional<DBUser> getUserByMSR(MSRTracks msrTracks) {
        return users.filter(u -> msrTracks.getTrack2().equals(u.getMsr())).findFirst();
    }
}
