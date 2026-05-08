package io.github.guilhermegodoydev.greenhorizon.core.entities.enemies;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import io.github.guilhermegodoydev.greenhorizon.core.utils.Assets;

public class EnemyPlasticBag extends EnemyBase {

    public EnemyPlasticBag(Array<Vector2> waypoints) {
        super(waypoints);
        this.texture = Assets.getTexture("gas.png");
        this.speed = 50f;
        this.health = 100;
        this.reward = 20; // RECOMPENSA REDUZIDA (era 35)
    }

    @Override
    public void render(SpriteBatch batch) {
        if (blinkTimer > 0) {
            batch.setColor(Color.RED);
        } else {
            batch.setColor(Color.LIGHT_GRAY);
        }

        batch.draw(texture, position.x - texture.getWidth()/2f, position.y - texture.getHeight()/2f);
        batch.setColor(Color.WHITE);
    }

    @Override
    protected void reachedEnd() {
        System.out.println("Uma Sacola Plástica poluiu a cidade!");
    }
}
