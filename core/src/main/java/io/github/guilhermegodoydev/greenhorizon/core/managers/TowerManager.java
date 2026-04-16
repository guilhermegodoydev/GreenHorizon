package io.github.guilhermegodoydev.greenhorizon.core.managers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import io.github.guilhermegodoydev.greenhorizon.core.entities.towers.TowerBase;
import io.github.guilhermegodoydev.greenhorizon.core.entities.towers.TowerTree;
import io.github.guilhermegodoydev.greenhorizon.core.map.TowerSlot;

public class TowerManager {
    private Array<TowerBase> towers;

    public TowerManager() {
        this.towers = new Array<>();
    }

    public void buildTower(TowerSlot slot) {
        if (!slot.isOccupied()) {
            TowerTree newTower = new TowerTree(slot.getCenterX(), slot.getCenterY());
            towers.add(newTower);

            slot.setOccupied(true);
        }
    }

    public void update(float delta) {
        for (TowerBase tower : towers) {
            tower.update(delta);
        }
    }

    public void render(SpriteBatch batch) {
        for (TowerBase tower : towers) {
            tower.render(batch);
        }
    }
}
