package io.github.guilhermegodoydev.greenhorizon;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameObject {
    private float x,y;
    private Texture texture;

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() { return y; }
    public void setY(float y) { this.y = y; }

    public Texture getTexture() { return texture; }

    public GameObject(Texture texture, float positionX, float positionY) {
        this.texture = texture;
        this.x = positionX;
        this.y = positionY;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y);
    }

    public void dispose() {
        if (texture != null) {
            texture.dispose();
        }
    }
}
