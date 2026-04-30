package io.github.guilhermegodoydev.greenhorizon.core.entities.towers;

import com.badlogic.gdx.graphics.g2d.Sprite;
import io.github.guilhermegodoydev.greenhorizon.core.map.TowerSlot;
import io.github.guilhermegodoydev.greenhorizon.core.utils.Assets;

public class TowerTree extends TowerBase {
    public static final int CUSTO = 50;

    public TowerTree(float x, float y, TowerSlot slot) {
        super(
            new Sprite(Assets.getTexture("torre_arvore_nivel1.png")),
            x,
            y,
            10f,
            80f,
            1.2f,
            slot
        );
    }

    @Override
    public void attack() {
        System.out.println("TowerTree está disparando folhas!");
    }
}
