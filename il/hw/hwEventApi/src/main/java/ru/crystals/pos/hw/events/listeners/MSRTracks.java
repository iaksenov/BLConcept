package ru.crystals.pos.hw.events.listeners;

import ru.crystals.pos.hw.events.HWHumanEvent;

import java.util.StringJoiner;

public class MSRTracks implements HWHumanEvent {

    private final String track1;
    private final String track2;
    private final String track3;
    private final String track4;

    public MSRTracks(String track1, String track2, String track3, String track4) {
        this.track1 = track1;
        this.track2 = track2;
        this.track3 = track3;
        this.track4 = track4;
    }

    public String getTrack1() {
        return track1;
    }

    public String getTrack2() {
        return track2;
    }

    public String getTrack3() {
        return track3;
    }

    public String getTrack4() {
        return track4;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", MSRTracks.class.getSimpleName() + "[", "]")
            .add("track1='" + track1 + "'")
            .add("track2='" + track2 + "'")
            .add("track3='" + track3 + "'")
            .add("track4='" + track4 + "'")
            .toString();
    }
}
