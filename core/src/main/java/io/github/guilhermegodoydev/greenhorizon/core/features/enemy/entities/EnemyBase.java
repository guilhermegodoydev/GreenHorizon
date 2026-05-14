package io.github.guilhermegodoydev.greenhorizon.core.features.enemy.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public abstract class EnemyBase {
    private Vector2 position;
    private Texture texture;
    private float speed;
    private int health;
    private int currentWaypointIndex = 0;
    private Array<Vector2> waypoints;
    private boolean active = true;
    private boolean damageDealt = false;
    private int reward;
    private float blinkTimer = 0f;
    private float slowMultiplier = 1.0f;

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

            float currentSpeed = speed * slowMultiplier;
            position.add(direction.scl(currentSpeed * delta));

            if (position.dst(target) < 2f) {
                currentWaypointIndex++;
            }
        } else {
            active = false;
        }

        slowMultiplier = 1.0f;
    }

    public void applySlow(float factor) {
        if (factor < slowMultiplier) {
            this.slowMultiplier = factor;
        }
    }

    public void render(SpriteBatch batch) {
        if (texture == null) return;

        if (blinkTimer > 0) {
            batch.setColor(Color.RED);
        } else {
            batch.setColor(Color.WHITE);
        }

        float width = texture.getWidth();
        float height = texture.getHeight();
        batch.draw(texture, position.x - width / 2f, position.y - height / 2f, width, height);

        batch.setColor(Color.WHITE);
    }

    protected abstract void reachedEnd();

    public void takeDamage(int damage) {
        this.health -= damage;
        this.blinkTimer = 0.15f;

        if (this.health <= 0) {
            this.health = 0;
            this.active = false;
            System.out.println(this.getClass().getSimpleName() + " was destroyed!");
        }
    }

    public boolean isDamageDealt() { return damageDealt; }
    public void setDamageDealt(boolean damageDealt) { this.damageDealt = damageDealt; }
    public boolean isActive() { return active; }
    public void setHealth(int health) { this.health = health; }
    public Vector2 getPosition() { return position; }
    public int getReward() { return reward; }
    public int getHealth() { return health; }
    public int getCurrentWaypointIndex() { return currentWaypointIndex; }
    public Array<Vector2> getWaypoints() { return waypoints; }

    protected void setTexture(Texture texture) { this.texture = texture; }
    protected void setSpeed(float speed) { this.speed = speed; }
    protected void setReward(int reward) { this.reward = reward; }
}
