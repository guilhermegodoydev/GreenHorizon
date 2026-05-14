package io.github.guilhermegodoydev.greenhorizon.core.features.tower.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import io.github.guilhermegodoydev.greenhorizon.core.features.enemy.entities.EnemyBase;

public class Projectile {
    private final Sprite sprite;
    private final Vector2 position;
    private final EnemyBase target;
    private final float damage;
    private boolean active = true;

    public Projectile(Sprite sprite, float x, float y, float damage, EnemyBase target) {
        this.sprite = sprite;
        this.position = new Vector2(x, y);
        this.damage = damage;
        this.target = target;
        this.sprite.setOriginCenter();
    }

    public void update(float delta) {
        if (target == null || !target.isActive()) {
            active = false;
            return;
        }

        Vector2 direction = target.getPosition().cpy().sub(position).nor();
        float speed = 250f;
        position.add(direction.scl(speed * delta));

        sprite.setRotation(direction.angleDeg());
        sprite.setPosition(position.x - sprite.getWidth() / 2, position.y - sprite.getHeight() / 2);

        if (position.dst(target.getPosition()) < 8f) {
            target.takeDamage((int) damage);
            active = false;
        }
    }

    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public boolean isActive() { return active; }
}
