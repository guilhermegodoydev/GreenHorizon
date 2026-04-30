package io.github.guilhermegodoydev.greenhorizon.core.entities.towers;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import io.github.guilhermegodoydev.greenhorizon.core.map.TowerSlot;

public abstract class TowerBase {
    protected Sprite sprite;
    protected Vector2 position;
    protected float damage;
    protected float range;
    protected float fireRate;
    protected float cooldownTimer;
    protected TowerSlot currentSlot;
    protected int valorVenda;

    public TowerBase(Sprite sprite, float x, float y, float damage, float range, float fireRate, TowerSlot slot) {
        this.sprite = sprite;
        this.position = new Vector2(x, y);
        this.damage = damage;
        this.range = range;
        this.fireRate = fireRate;
        this.cooldownTimer = 0;
        this.currentSlot = slot;
        this.valorVenda = 35;

        this.sprite.setPosition(x - sprite.getWidth() / 2, y - sprite.getHeight() / 2);
    }

    public void update(float delta) {
        //cooldownTimer += delta;
        //if (cooldownTimer >= fireRate) {
        //    attack();
        //    cooldownTimer = 0;
        //}
    }

    public abstract void attack();

    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public Vector2 getPosition() { return position; }

    public TowerSlot getCurrentSlot() { return currentSlot; }
    public int getValorVenda() { return valorVenda; }
}
