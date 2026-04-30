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

    @Override
    public int getCustoUpgrade() {
        if (nivel == 1) return 30;
        if (nivel == 2) return 60;
        return 9999;
    }

    @Override
    public void aplicarMelhoriaStatus() {
        this.damage *= 1.5f;
        this.range += 10f;

        if (this.nivel == 2) {
            this.sprite.setRegion(Assets.getTexture("torre_arvore_nivel2.png"));
        } else if (this.nivel == 3) {
            this.sprite.setRegion(Assets.getTexture("torre_arvore_nivel3.png"));
        }

        this.sprite.setSize(this.sprite.getRegionWidth(), this.sprite.getRegionHeight());

        System.out.println("Upgrade visual concluído para o nível " + nivel);
    }
}
