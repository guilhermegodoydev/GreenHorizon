package io.github.guilhermegodoydev.greenhorizon.core.entities.enemies;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import io.github.guilhermegodoydev.greenhorizon.core.utils.Assets;

public class EnemyPlasticBag extends EnemyBase {

    public EnemyPlasticBag(Array<Vector2> waypoints) {
        super(waypoints);
        this.texture = Assets.getTexture("gas.png"); // Placeholder
        this.speed = 50f; // Muito mais lento que o Gás
        this.health = 100; // 5x mais vida
        this.reward = 35; // Recompensa gorda
    }

    @Override
    public void render(SpriteBatch batch) {
        // Lógica de Feedback Visual (Piscar Vermelho)
        if (blinkTimer > 0) {
            batch.setColor(Color.RED);
        } else {
            batch.setColor(Color.LIGHT_GRAY); // A cor Graybox da Sacola
        }

        batch.draw(texture, position.x - texture.getWidth()/2f, position.y - texture.getHeight()/2f);

        // Reseta a cor para não pintar o resto do jogo de cinza
        batch.setColor(Color.WHITE);
    }

    @Override
    protected void reachedEnd() {
        System.out.println("Uma Sacola Plástica poluiu a cidade!");
    }
}
