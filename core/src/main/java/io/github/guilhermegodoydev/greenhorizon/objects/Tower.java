package io.github.guilhermegodoydev.greenhorizon.objects;

import com.badlogic.gdx.graphics.Texture;
import io.github.guilhermegodoydev.greenhorizon.GameObject;

public class Tower extends GameObject {
    private int value;
    private int valueSale;
    private int damage;

    public int getValue() { return value; }
    public int getValueSale() { return valueSale; }

    public Tower(Texture texture, float positionX, float positionY, int valueSale, int value, int damage) {
        super(texture, positionX, positionY);

        this.valueSale = valueSale;
        this.value = value;
        this.damage = damage;
    }
}
