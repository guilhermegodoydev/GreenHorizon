package io.github.guilhermegodoydev.greenhorizon.core.entities.towers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class TowerTree extends TowerBase {

    public TowerTree(float x, float y) {
        super(new Sprite(new Texture("torre_arvore_nivel1.png")), x, y, 10f, 80f, 1.2f);
    }

    @Override
    public void attack() {
        System.out.println("TowerTree está disparando folhas!");
        cooldownTimer = 0;
    }
}
