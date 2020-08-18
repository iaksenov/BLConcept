package ru.crystals.pos.docs.data;


import java.util.ArrayList;
import java.util.List;

public class Purchase {

    private final List<Position> positions = new ArrayList<>();

    public Purchase() {
    }

    public List<Position> getPositions() {
        return positions;
    }

}
