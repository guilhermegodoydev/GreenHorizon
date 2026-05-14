package io.github.guilhermegodoydev.greenhorizon.core.features.tower.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import io.github.guilhermegodoydev.greenhorizon.core.features.enemy.entities.EnemyBase;
import io.github.guilhermegodoydev.greenhorizon.core.map.TowerSlot;
import io.github.guilhermegodoydev.greenhorizon.core.features.economy.CoinsManager;

public abstract class TowerBase {
    private Sprite sprite;
    private Vector2 position;
    private TowerSlot currentSlot;
    private int sellValue;
    private int level = 1;
    public static final int MAX_LEVEL = 3;

    public TowerBase(Sprite sprite, float x, float y, TowerSlot slot) {
        this.sprite = sprite;
        this.position = new Vector2(x, y);
        this.currentSlot = slot;
        this.sellValue = 35;
        this.sprite.setPosition(x - sprite.getWidth() / 2, y - sprite.getHeight() / 2);
    }

    public abstract void update(float delta, Array<EnemyBase> enemies, Array<Projectile> projectiles, CoinsManager coinsManager);

    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public Sprite getSprite() { return sprite; }
    public Vector2 getPosition() { return position; }
    public TowerSlot getCurrentSlot() { return currentSlot; }
    public int getSellValue() { return sellValue; }
    public void setSellValue(int sellValue) { this.sellValue = sellValue; }
    public int getLevel() { return level; }
    public void upgradeLevel() { this.level++; }
    public abstract int getUpgradeCost();
    public abstract void applyStatusUpgrade();
}
