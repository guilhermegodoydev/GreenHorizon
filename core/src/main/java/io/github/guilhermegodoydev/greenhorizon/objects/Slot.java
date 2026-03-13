package io.github.guilhermegodoydev.greenhorizon.objects;

import com.badlogic.gdx.graphics.Texture;
import io.github.guilhermegodoydev.greenhorizon.GameObject;

public class Slot extends GameObject {
    private Tower tower;

    public Slot(Texture texture, float positionX, float positionY) {
        super(texture, positionX, positionY);

        this.tower = null;
    }

    public Tower getTower() { return tower; }
    public void setTower(Tower tower) { this.tower = tower; }
}
