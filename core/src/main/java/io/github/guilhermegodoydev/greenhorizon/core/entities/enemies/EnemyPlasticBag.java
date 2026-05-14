package io.github.guilhermegodoydev.greenhorizon.core.entities.enemies;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import io.github.guilhermegodoydev.greenhorizon.core.utils.Assets;

public class EnemyPlasticBag extends EnemyBase {

    public EnemyPlasticBag(Array<Vector2> waypoints) {
        super(waypoints);
        setTexture(Assets.getTexture("plasticbag.png"));
        setSpeed(50f);
        setHealth(100);
        setReward(20);
    }

    @Override
    protected void reachedEnd() {
        System.out.println("A Plastic Bag polluted the city!");
    }
}
