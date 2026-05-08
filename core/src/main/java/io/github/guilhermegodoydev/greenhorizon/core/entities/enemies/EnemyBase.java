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

    // NOVA VARIÁVEL: Controla o tempo que o inimigo fica vermelho
    protected float blinkTimer = 0f;

    public boolean isDanoCausado() { return danoCausado; }
    public void setDanoCausado(boolean danoCausado) { this.danoCausado = danoCausado; }

    public EnemyBase(Array<Vector2> waypoints) {
        this.waypoints = waypoints;
        this.position = new Vector2(waypoints.get(0).x, waypoints.get(0).y);
    }

    public void update(float delta) {
        // Lógica do blink: diminui o timer se for maior que zero
        if (blinkTimer > 0) {
            blinkTimer -= delta;
        }

        if (currentWaypointIndex < waypoints.size) {
            Vector2 target = waypoints.get(currentWaypointIndex);
            Vector2 direction = target.cpy().sub(position).nor();
            position.add(direction.scl(speed * delta));

            if (position.dst(target) < 2f) {
                currentWaypointIndex++;
            }
        } else {
            active = false;
        }
    }

    public abstract void render(SpriteBatch batch);

    protected abstract void reachedEnd();

    public void takeDamage(int damage) {
        this.health -= damage;
        this.blinkTimer = 0.15f; // PISCA POR 0.15 SEGUNDOS

        if (this.health <= 0) {
            this.health = 0;
            this.active = false; // Isso fará com que o EnemyManager o remova no próximo update
            System.out.println(this.getClass().getSimpleName() + " foi destruído!");
        }
    }

    public boolean isActive() { return active; }
    public void setHealth(int health) { this.health = health; }
    public Vector2 getPosition() { return position; }
    public int getReward() { return reward; }
    public int getHealth() { return health; }

    // --- NOVOS GETTERS PARA A IA DA TORRE ---
    public int getCurrentWaypointIndex() { return currentWaypointIndex; }
    public Array<Vector2> getWaypoints() { return waypoints; }
}
