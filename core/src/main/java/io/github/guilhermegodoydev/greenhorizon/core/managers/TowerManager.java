package io.github.guilhermegodoydev.greenhorizon.core.managers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import io.github.guilhermegodoydev.greenhorizon.core.entities.enemies.EnemyBase;
import io.github.guilhermegodoydev.greenhorizon.core.entities.projectiles.Projectile;
import io.github.guilhermegodoydev.greenhorizon.core.entities.towers.TowerBase;
import io.github.guilhermegodoydev.greenhorizon.core.entities.towers.TowerTree;
import io.github.guilhermegodoydev.greenhorizon.core.entities.towers.TowerSolar;
import io.github.guilhermegodoydev.greenhorizon.core.entities.towers.TowerWind;
import io.github.guilhermegodoydev.greenhorizon.core.exceptions.InsufficientFundsException;
import io.github.guilhermegodoydev.greenhorizon.core.itens.CoinsManager;
import io.github.guilhermegodoydev.greenhorizon.core.map.TowerSlot;

public class TowerManager {
    private Array<TowerBase> towers;
    private Array<Projectile> projectiles;

    public TowerManager() {
        this.towers = new Array<>();
        this.projectiles = new Array<>();
    }

    public TowerBase getTowerAt(float x, float y) {
        for (TowerBase tower : towers) {
            if (tower.getPosition().dst(x, y) < 1f) {
                return tower;
            }
        }
        return null;
    }

    public void buildTower(TowerSlot slot, String type, int cost, CoinsManager coinsManager) {
        if (slot.isOccupied()) return;

        try {
            coinsManager.remove(cost);

            TowerBase newTower = TowerFactory.createTower(type, slot);

            if (newTower != null) {
                towers.add(newTower);
                slot.setOccupied(true);
                System.out.println("Tower " + type + " built! Cost: " + cost);
            }
        } catch (InsufficientFundsException e) {
            System.err.println(e.getMessage());
        }
    }

    public void update(float delta, Array<EnemyBase> enemies, CoinsManager coinsManager) {
        for (TowerBase tower : towers) {
            tower.update(delta, enemies, projectiles, coinsManager);
        }

        for (int i = projectiles.size - 1; i >= 0; i--) {
            Projectile p = projectiles.get(i);
            p.update(delta);
            if (!p.isActive()) {
                projectiles.removeIndex(i);
            }
        }
    }

    public void render(SpriteBatch batch) {
        for (TowerBase tower : towers) {
            tower.render(batch);
        }
        for (Projectile p : projectiles) {
            p.render(batch);
        }
    }

    public void sellTower(TowerBase tower) {
        if (tower != null) {
            if (tower.getCurrentSlot() != null) {
                tower.getCurrentSlot().setOccupied(false);
            }
            towers.removeValue(tower, true);
            System.out.println("Tower removed from Manager.");
        }
    }

    public void upgradeTower(TowerBase tower, CoinsManager coinsManager) {
        if (tower == null || tower.getLevel() >= TowerBase.MAX_LEVEL) return;

        int cost = tower.getUpgradeCost();

        try {
            coinsManager.remove(cost);
            tower.upgradeLevel();
            tower.applyStatusUpgrade();
        } catch (InsufficientFundsException e) {
            System.err.println(e.getMessage());
        }
    }
}
