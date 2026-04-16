package io.github.guilhermegodoydev.greenhorizon.core.map;

import com.badlogic.gdx.math.Rectangle;

public class TowerSlot {
    private final Rectangle bounds;
    private boolean occupied;

    public TowerSlot(Rectangle bounds) {
        this.bounds = bounds;
        this.occupied = false;
    }

    public boolean isHit(float x, float y) {
        return bounds.contains(x, y);
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public float getCenterX() {
        return bounds.x + bounds.width / 2;
    }

    public float getCenterY() {
        return bounds.y + bounds.height / 2;
    }

    public Rectangle getBounds() {
        return bounds;
    }

}
