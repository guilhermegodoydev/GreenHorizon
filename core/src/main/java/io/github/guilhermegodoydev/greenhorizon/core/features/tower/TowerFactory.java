package io.github.guilhermegodoydev.greenhorizon.core.features.tower;

import io.github.guilhermegodoydev.greenhorizon.core.features.tower.entities.TowerBase;
import io.github.guilhermegodoydev.greenhorizon.core.features.tower.entities.TowerSolar;
import io.github.guilhermegodoydev.greenhorizon.core.features.tower.entities.TowerTree;
import io.github.guilhermegodoydev.greenhorizon.core.features.tower.entities.TowerWind;
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
