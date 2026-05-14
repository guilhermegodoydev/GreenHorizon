package io.github.guilhermegodoydev.greenhorizon.core.features.tower.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import io.github.guilhermegodoydev.greenhorizon.core.features.enemy.entities.EnemyBase;
import io.github.guilhermegodoydev.greenhorizon.core.map.TowerSlot;
import io.github.guilhermegodoydev.greenhorizon.core.features.economy.CoinsManager;

public abstract class AttackTower extends TowerBase {
    private float damage;
    private float range;
    private float fireRate;
    private float cooldownTimer;

    public AttackTower(Sprite sprite, float x, float y, float damage, float range, float fireRate, TowerSlot slot) {
        super(sprite, x, y, slot);
        this.damage = damage;
        this.range = range;
        this.fireRate = fireRate;
        this.cooldownTimer = 0;
    }

    @Override
    public void update(float delta, Array<EnemyBase> enemies, Array<Projectile> projectiles, CoinsManager coinsManager) {
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
            if (enemy.isActive() && getPosition().dst(enemy.getPosition()) <= range) {
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

    public float getDamage() { return damage; }
    public void setDamage(float damage) { this.damage = damage; }
    public float getRange() { return range; }
    public void setRange(float range) { this.range = range; }
    public float getFireRate() { return fireRate; }
    public void setFireRate(float fireRate) { this.fireRate = fireRate; }
}
