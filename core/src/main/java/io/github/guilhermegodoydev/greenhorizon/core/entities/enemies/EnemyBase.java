package io.github.guilhermegodoydev.greenhorizon.core.entities.enemies;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public abstract class EnemyBase {
    protected Vector2 position;
    protected Texture texture;
    protected float speed;
    protected int health;
    protected int currentWaypointIndex = 0;
    protected Array<Vector2> waypoints;
    protected boolean active = true;
    private boolean danoCausado = false;
    protected int reward;
    protected float blinkTimer = 0f;

    // NOVO: Multiplicador de velocidade
    protected float slowMultiplier = 1.0f;

    public EnemyBase(Array<Vector2> waypoints) {
        this.waypoints = waypoints;
        this.position = new Vector2(waypoints.get(0).x, waypoints.get(0).y);
    }

    public void update(float delta) {
        if (blinkTimer > 0) {
            blinkTimer -= delta;
        }

        if (currentWaypointIndex < waypoints.size) {
            Vector2 target = waypoints.get(currentWaypointIndex);
            Vector2 direction = target.cpy().sub(position).nor();

            // APLICA A LENTIDÃO AQUI
            float currentSpeed = speed * slowMultiplier;
            position.add(direction.scl(currentSpeed * delta));

            if (position.dst(target) < 2f) {
                currentWaypointIndex++;
            }
        } else {
            active = false;
        }

        // RESET DO SLOW: Garante que ele volte ao normal se sair do raio da torre
        slowMultiplier = 1.0f;
    }

    // NOVO: Método que as torres chamam para aplicar lentidão
    public void applySlow(float factor) {
        if (factor < slowMultiplier) {
            this.slowMultiplier = factor;
        }
    }

    public abstract void render(SpriteBatch batch);
    protected abstract void reachedEnd();

    public void takeDamage(int damage) {
        this.health -= damage;
        this.blinkTimer = 0.15f;

        if (this.health <= 0) {
            this.health = 0;
            this.active = false;
            System.out.println(this.getClass().getSimpleName() + " foi destruído!");
        }
    }

    public boolean isDanoCausado() { return danoCausado; }
    public void setDanoCausado(boolean danoCausado) { this.danoCausado = danoCausado; }
    public boolean isActive() { return active; }
    public void setHealth(int health) { this.health = health; }
    public Vector2 getPosition() { return position; }
    public int getReward() { return reward; }
    public int getHealth() { return health; }
    public int getCurrentWaypointIndex() { return currentWaypointIndex; }
    public Array<Vector2> getWaypoints() { return waypoints; }
}
