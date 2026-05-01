package io.github.guilhermegodoydev.greenhorizon.core.entities.projectiles;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import io.github.guilhermegodoydev.greenhorizon.core.entities.enemies.EnemyBase;

public class Projectile {
    private Sprite sprite;
    private Vector2 position;
    private EnemyBase target;
    private float speed = 250f;
    private float damage;
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

        // Movimentação em direção ao inimigo
        Vector2 direction = target.getPosition().cpy().sub(position).nor();
        position.add(direction.scl(speed * delta));

        // Rotação do sprite (opcional para dar efeito visual)
        sprite.setRotation(direction.angleDeg());
        sprite.setPosition(position.x - sprite.getWidth() / 2, position.y - sprite.getHeight() / 2);

        // Verificação de colisão (Ajuste o valor 8f de acordo com o tamanho do seu inimigo)
        if (position.dst(target.getPosition()) < 8f) {
            target.takeDamage((int) damage); // Aplica o dano
            active = false; // O projétil some após atingir
        }
    }

    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public boolean isActive() { return active; }
}
