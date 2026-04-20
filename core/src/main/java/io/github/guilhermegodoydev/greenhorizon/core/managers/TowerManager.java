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

    public TowerBase getTowerAt(float x, float y) {
        for (TowerBase tower : towers) {
            if (tower.getPosition().dst(x, y) < 1f) {
                return tower;
            }
        }
        return null;
    }

    public void buildTower(TowerSlot slot, String tipo) {
        if (!slot.isOccupied()) {
            TowerBase newTower = null;

            if (tipo.equalsIgnoreCase("Arvore")) {
                newTower = new TowerTree(slot.getCenterX(), slot.getCenterY());
            }
            // Adicione aqui as outras quando criar as classes:
            // else if (tipo.equalsIgnoreCase("Eolica")) { newTower = new TowerEolica(...); }

            if (newTower != null) {
                towers.add(newTower);
                slot.setOccupied(true);
                System.out.println("Torre " + tipo + " construída com sucesso!");
            }
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
