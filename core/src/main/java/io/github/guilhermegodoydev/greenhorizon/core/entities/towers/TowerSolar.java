package io.github.guilhermegodoydev.greenhorizon.core.entities.towers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import io.github.guilhermegodoydev.greenhorizon.core.entities.enemies.EnemyBase;
import io.github.guilhermegodoydev.greenhorizon.core.entities.projectiles.Projectile;
import io.github.guilhermegodoydev.greenhorizon.core.map.TowerSlot;
import io.github.guilhermegodoydev.greenhorizon.core.utils.Assets;
import io.github.guilhermegodoydev.greenhorizon.core.itens.CoinsManager;

public class TowerSolar extends TowerBase {
    public static final int COST = 150;

    private float productionTimer = 0f;
    private float productionInterval = 10f;
    private int moneyAmount = 15;

    public TowerSolar(float x, float y, TowerSlot slot) {
        super(new Sprite(Assets.getTexture("torre_solar_nivel1.png")), x, y, slot);
        setSellValue(75);
    }

    @Override
    public void update(float delta, Array<EnemyBase> enemies, Array<Projectile> projectiles, CoinsManager coinsManager) {
        productionTimer += delta;
        if (productionTimer >= productionInterval) {
            coinsManager.add(moneyAmount);
            productionTimer = 0f;
            System.out.println("Solar Panel generated $" + moneyAmount + "!");
        }
    }

    @Override
    public int getUpgradeCost() {
        if (getLevel() == 1) return 80;
        if (getLevel() == 2) return 120;
        return 9999;
    }

    @Override
    public void applyStatusUpgrade() {
        if (getLevel() == 2) {
            this.moneyAmount = 25;
            this.productionInterval = 8f;
            getSprite().setRegion(Assets.getTexture("torre_solar_nivel2.png"));
        } else if (getLevel() == 3) {
            this.moneyAmount = 50;
            this.productionInterval = 6f;
            getSprite().setRegion(Assets.getTexture("torre_solar_nivel3.png"));
        }

        getSprite().setSize(getSprite().getRegionWidth(), getSprite().getRegionHeight());
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.setColor(Color.YELLOW);
        super.render(batch);
        batch.setColor(Color.WHITE);
    }
}
