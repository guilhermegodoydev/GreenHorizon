package io.github.guilhermegodoydev.greenhorizon.core.features.enemy.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import io.github.guilhermegodoydev.greenhorizon.core.utils.Assets;

public class EnemyGas extends EnemyBase {

    public EnemyGas(Array<Vector2> waypoints) {
        super(waypoints);
        setTexture(Assets.getTexture("gas.png"));
        setSpeed(80f);
        setHealth(20);
        setReward(8);
    }

    @Override
    protected void reachedEnd() {
        System.out.println("The polluting gas reached the city!");
    }
}
