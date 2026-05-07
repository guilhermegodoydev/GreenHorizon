package io.github.guilhermegodoydev.greenhorizon.core.managers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import io.github.guilhermegodoydev.greenhorizon.core.entities.enemies.EnemyBase;
import io.github.guilhermegodoydev.greenhorizon.core.itens.CoinsManager;
import io.github.guilhermegodoydev.greenhorizon.core.itens.LifeManager;

public class EnemyManager {
    private Array<EnemyBase> enemies;
    private Array<Vector2> waypoints;
    private LifeManager lifeManager;
    private CoinsManager coinsManager;

    public EnemyManager(Array<Vector2> waypoints, LifeManager lifeManager, CoinsManager coinsManager) {
        this.enemies = new Array<>();
        this.waypoints = waypoints;
        this.lifeManager = lifeManager;
        this.coinsManager = coinsManager; // Inicializa aqui
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

            if (!e.isActive()) {
                Vector2 lastWaypoint = waypoints.get(waypoints.size - 1);

                // LÓGICA DE RECOMPENSA:
                // Se ele morreu (vida <= 0) E NÃO chegou ao fim do mapa
                if (e.getHealth() <= 0 && e.getPosition().dst(lastWaypoint) > 10f) {
                    coinsManager.acrescentar(e.getReward());
                }
                // LÓGICA DE PERDA DE VIDA (Sua lógica atual melhorada)
                else if (!e.isDanoCausado() && e.getPosition().dst(lastWaypoint) < 10f) {
                    e.setDanoCausado(true);
                    lifeManager.perderVida(1);
                }

                enemies.removeIndex(i);
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
