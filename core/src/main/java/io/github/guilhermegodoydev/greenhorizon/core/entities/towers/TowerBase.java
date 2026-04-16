package io.github.guilhermegodoydev.greenhorizon.core.entities.towers;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class TowerBase {
    protected Sprite sprite;
    protected Vector2 position;
    protected float damage;
    protected float range;
    protected float fireRate;
    protected float cooldownTimer;

    public TowerBase(Sprite sprite, float x, float y, float damage, float range, float fireRate) {
        this.sprite = sprite;
        this.position = new Vector2(x, y);
        this.damage = damage;
        this.range = range;
        this.fireRate = fireRate;
        this.cooldownTimer = 0;

        this.sprite.setPosition(x - sprite.getWidth() / 2, y - sprite.getHeight() / 2);
    }

    public void update(float delta) {
        cooldownTimer += delta;
        if (cooldownTimer >= fireRate) {
            attack();
        }
    }

    public abstract void attack();

    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public Vector2 getPosition() { return position; }
}
