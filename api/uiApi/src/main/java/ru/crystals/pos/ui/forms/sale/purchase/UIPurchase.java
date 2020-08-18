package ru.crystals.pos.ui.forms.sale.purchase;

import java.util.ArrayList;
import java.util.List;

public class UIPurchase {

    private final List<UIPosition> positions = new ArrayList<>();

    public UIPurchase() {
    }

    public List<UIPosition> getPositions() {
        return positions;
    }
}
