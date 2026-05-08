package io.github.guilhermegodoydev.greenhorizon.core.entities.towers;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import io.github.guilhermegodoydev.greenhorizon.core.entities.enemies.EnemyBase;
import io.github.guilhermegodoydev.greenhorizon.core.entities.projectiles.Projectile;
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
    public void attack(EnemyBase target, Array<Projectile> projectiles) {
        Sprite leafSprite = new Sprite(Assets.getTexture("tiro_folha.png"));
        projectiles.add(new Projectile(leafSprite, position.x, position.y, damage, target));
    }

    @Override
    public int getCustoUpgrade() {
        if (nivel == 1) return 50; // NOVO PREÇO LV2
        if (nivel == 2) return 100; // NOVO PREÇO LV3
        return 9999;
    }

    @Override
    public void aplicarMelhoriaStatus() {
        this.range += 10f;

        if (this.nivel == 2) {
            this.damage = 15f;
            this.sprite.setRegion(Assets.getTexture("torre_arvore_nivel2.png"));
        } else if (this.nivel == 3) {
            this.damage = 25f;
            this.sprite.setRegion(Assets.getTexture("torre_arvore_nivel3.png"));
        }

        this.sprite.setSize(this.sprite.getRegionWidth(), this.sprite.getRegionHeight());
    }
}
