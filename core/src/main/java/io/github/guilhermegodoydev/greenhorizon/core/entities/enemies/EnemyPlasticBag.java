package io.github.guilhermegodoydev.greenhorizon.core.entities.enemies;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import io.github.guilhermegodoydev.greenhorizon.core.utils.Assets;

public class EnemyPlasticBag extends EnemyBase {

    public EnemyPlasticBag(Array<Vector2> waypoints) {
        super(waypoints);
        // TEXTURA ATUALIZADA PARA O ASSET OFICIAL
        this.texture = Assets.getTexture("plasticbag.png");
        this.speed = 50f;
        this.health = 100;
        this.reward = 20;
    }

    @Override
    public void render(SpriteBatch batch) {
        // Lógica de Feedback Visual mantida
        if (blinkTimer > 0) {
            batch.setColor(Color.RED);
        } else {
            // Removido o filtro cinza de teste, agora exibe a cor natural da imagem
            batch.setColor(Color.WHITE);
        }

        batch.draw(texture, position.x - texture.getWidth()/2f, position.y - texture.getHeight()/2f);

        // Reseta a cor para não pintar o resto do jogo
        batch.setColor(Color.WHITE);
    }

    @Override
    protected void reachedEnd() {
        System.out.println("Uma Sacola Plástica poluiu a cidade!");
    }
}
