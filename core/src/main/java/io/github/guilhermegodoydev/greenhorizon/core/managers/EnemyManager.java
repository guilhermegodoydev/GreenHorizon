package io.github.guilhermegodoydev.greenhorizon.core.managers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import io.github.guilhermegodoydev.greenhorizon.core.entities.enemies.EnemyBase;
import io.github.guilhermegodoydev.greenhorizon.core.itens.LifeManager;

public class EnemyManager {
    private Array<EnemyBase> enemies;
    private Array<Vector2> waypoints;
    private LifeManager lifeManager;

    public EnemyManager(Array<Vector2> waypoints, LifeManager lifeManager) {
        this.enemies = new Array<>();
        this.waypoints = waypoints;
        this.lifeManager = lifeManager;
    }

    public void spawnEnemy(String type) {
        EnemyBase newEnemy = EnemyFactory.createEnemy(type, waypoints);
        if (newEnemy != null) {
            enemies.add(newEnemy);
        }
    }

    public void update(float delta) {
        for (int i = enemies.size - 1; i >= 0; i--) {
            EnemyBase e = enemies.get(i);
            e.update(delta);

            // Se o inimigo chegou ao fim E ainda não causou dano
            if (!e.isActive() && !e.isDanoCausado()) {
                e.setDanoCausado(true); // Marca que ele já deu o dano dele
                lifeManager.perderVida(1);
                enemies.removeIndex(i); // Remove imediatamente
            }
        }
    }

    public void render(SpriteBatch batch) {
        for (EnemyBase e : enemies) {
            e.render(batch);
        }
    }

    public Array<EnemyBase> getEnemies() { return enemies; }
}
