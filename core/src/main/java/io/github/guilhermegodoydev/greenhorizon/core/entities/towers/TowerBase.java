package io.github.guilhermegodoydev.greenhorizon.core.entities.towers;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import io.github.guilhermegodoydev.greenhorizon.core.entities.enemies.EnemyBase;
import io.github.guilhermegodoydev.greenhorizon.core.entities.projectiles.Projectile;
import io.github.guilhermegodoydev.greenhorizon.core.map.TowerSlot;
import io.github.guilhermegodoydev.greenhorizon.core.itens.CoinsManager; // IMPORT NECESSÁRIO

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

    // MÉTODO NOVO: Define se a torre procura alvos e atira (padrão: sim)
    public boolean isAtacante() { return true; }

    // ATUALIZADO: Recebe o CoinsManager para torres que geram dinheiro
    public void update(float delta, Array<EnemyBase> enemies, Array<Projectile> projectiles, CoinsManager coinsManager) {
        if (!isAtacante()) return; // Torres passivas (como a Solar) sobresscrevem esse método

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
        EnemyBase bestTarget = null;
        float furthestProgress = -1;

        for (EnemyBase enemy : enemies) {
            if (enemy.isActive() && position.dst(enemy.getPosition()) <= range) {
                if (enemy.getCurrentWaypointIndex() < enemy.getWaypoints().size) {
                    Vector2 nextWaypoint = enemy.getWaypoints().get(enemy.getCurrentWaypointIndex());
                    float distanceToNext = enemy.getPosition().dst(nextWaypoint);
                    float progress = (enemy.getCurrentWaypointIndex() * 1000) - distanceToNext;

                    if (progress > furthestProgress) {
                        furthestProgress = progress;
                        bestTarget = enemy;
                    }
                }
            }
        }
        return bestTarget;
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
