package io.github.guilhermegodoydev.greenhorizon.core.entities.towers;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class Tower {
    protected Vector2 posicao;
    protected Sprite sprite;
    protected float dano;
    protected float alcance;
    protected float tempoRecarga;

    public Tower(float x, float y) {
        this.posicao = new Vector2(x, y);
    }

    public abstract void update(float delta);

    public void render(SpriteBatch batch) {
        if (sprite != null) {
            sprite.setPosition(posicao.x, posicao.y);
            sprite.draw(batch);
        }
    }

    public Vector2 getPosicao() { return posicao; }
    public float getAlcance() { return alcance; }
}
