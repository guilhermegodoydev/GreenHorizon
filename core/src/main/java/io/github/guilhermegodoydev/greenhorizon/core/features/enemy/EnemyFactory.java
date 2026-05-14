package io.github.guilhermegodoydev.greenhorizon.core.features.enemy;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import io.github.guilhermegodoydev.greenhorizon.core.features.enemy.entities.EnemyBase;
import io.github.guilhermegodoydev.greenhorizon.core.features.enemy.entities.EnemyGas;
import io.github.guilhermegodoydev.greenhorizon.core.features.enemy.entities.EnemyPlasticBag;

public class EnemyFactory {
    public static EnemyBase createEnemy(String type, Array<Vector2> waypoints) {
        switch (type.toUpperCase()) {
            case "GAS":
                return new EnemyGas(waypoints);
            case "BAG":
                return new EnemyPlasticBag(waypoints);
            default:
                return null;
        }
    }
}
