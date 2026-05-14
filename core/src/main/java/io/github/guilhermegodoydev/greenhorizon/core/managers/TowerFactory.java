package io.github.guilhermegodoydev.greenhorizon.core.managers;

import io.github.guilhermegodoydev.greenhorizon.core.entities.towers.*;
import io.github.guilhermegodoydev.greenhorizon.core.map.TowerSlot;

public class TowerFactory {

    public static TowerBase createTower(String type, TowerSlot slot) {
        if (type.equalsIgnoreCase("Tree")) {
            return new TowerTree(slot.getCenterX(), slot.getCenterY(), slot);
        } else if (type.equalsIgnoreCase("Solar")) {
            return new TowerSolar(slot.getCenterX(), slot.getCenterY(), slot);
        } else if (type.equalsIgnoreCase("Wind")) {
            return new TowerWind(slot.getCenterX(), slot.getCenterY(), slot);
        }
        return null;
    }
}
