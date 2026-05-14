package io.github.guilhermegodoydev.greenhorizon.core.features.tower.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import io.github.guilhermegodoydev.greenhorizon.core.features.enemy.entities.EnemyBase;
import io.github.guilhermegodoydev.greenhorizon.core.map.TowerSlot;
import io.github.guilhermegodoydev.greenhorizon.core.utils.Assets;
import io.github.guilhermegodoydev.greenhorizon.core.features.economy.CoinsManager;

public class TowerWind extends TowerBase {
    public static final int COST = 100;
    private float slowFactor = 0.6f;
    private float range = 120f;

    public TowerWind(float x, float y, TowerSlot slot) {
        super(new Sprite(Assets.getTexture("torre_eolica_nivel1.png")), x, y, slot);
        setSellValue(50);
    }

    @Override
    public void update(float delta, Array<EnemyBase> enemies, Array<Projectile> projectiles, CoinsManager coinsManager) {
        for (EnemyBase enemy : enemies) {
            if (enemy.isActive() && getPosition().dst(enemy.getPosition()) <= range) {
                enemy.applySlow(slowFactor);
            }
        }
    }

    @Override
    public int getUpgradeCost() {
        return (getLevel() == 1) ? 60 : 100;
    }

    @Override
    public void applyStatusUpgrade() {
        if (getLevel() == 2) {
            this.slowFactor = 0.45f;
            this.range += 20f;
            getSprite().setRegion(Assets.getTexture("torre_eolica_nivel2.png"));
        } else if (getLevel() == 3) {
            this.slowFactor = 0.3f;
            this.range += 30f;
            getSprite().setRegion(Assets.getTexture("torre_eolica_nivel3.png"));
        }

        getSprite().setSize(getSprite().getRegionWidth(), getSprite().getRegionHeight());
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.setColor(Color.CYAN);
        super.render(batch);
        batch.setColor(Color.WHITE);
    }

}
