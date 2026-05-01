package io.github.guilhermegodoydev.greenhorizon.core.entities.enemies;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import io.github.guilhermegodoydev.greenhorizon.core.utils.Assets;

public class EnemyGas extends EnemyBase {

    public EnemyGas(Array<Vector2> waypoints) {
        super(waypoints);
        this.texture = Assets.getTexture("gas.png"); // Nome do arquivo nos seus assets
        this.speed = 80f;
        this.health = 20;
    }

    @Override
    public void render(SpriteBatch batch) {
        // Desenha centralizado na posição
        batch.draw(texture, position.x - texture.getWidth()/2f, position.y - texture.getHeight()/2f);
    }

    @Override
    protected void reachedEnd() {
        System.out.println("O Gás poluente atingiu a cidade!");
        // Aqui depois chamaremos o LifeManager
    }
}
