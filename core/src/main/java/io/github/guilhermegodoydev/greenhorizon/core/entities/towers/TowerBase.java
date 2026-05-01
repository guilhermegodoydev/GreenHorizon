package io.github.guilhermegodoydev.greenhorizon.core.entities.towers;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import io.github.guilhermegodoydev.greenhorizon.core.entities.enemies.EnemyBase;
import io.github.guilhermegodoydev.greenhorizon.core.entities.projectiles.Projectile;
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
    protected int nivel = 1;
    public static final int NIVEL_MAXIMO = 3;

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

    // ATUALIZADO: Agora o método recebe delta, a lista de inimigos e a lista de projéteis
    public void update(float delta, Array<EnemyBase> enemies, Array<Projectile> projectiles) {
        cooldownTimer += delta;
        if (cooldownTimer >= fireRate) {
            EnemyBase target = findTarget(enemies);
            if (target != null) {
                attack(target, projectiles);
                cooldownTimer = 0;
            }
        }
    }

    private EnemyBase findTarget(Array<EnemyBase> enemies) {
        for (EnemyBase enemy : enemies) {
            if (enemy.isActive() && position.dst(enemy.getPosition()) <= range) {
                return enemy;
            }
        }
        return null;
    }

    public abstract void attack(EnemyBase target, Array<Projectile> projectiles);

    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public Vector2 getPosition() { return position; }
    public TowerSlot getCurrentSlot() { return currentSlot; }
    public int getValorVenda() { return valorVenda; }
    public int getNivel() { return nivel; }
    public void subirNivel() { this.nivel++; }
    public abstract int getCustoUpgrade();
    public abstract void aplicarMelhoriaStatus();
}
