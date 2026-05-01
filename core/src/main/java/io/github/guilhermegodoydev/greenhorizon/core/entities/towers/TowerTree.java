package io.github.guilhermegodoydev.greenhorizon.core.entities.towers;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array; // IMPORTANTE
import io.github.guilhermegodoydev.greenhorizon.core.entities.enemies.EnemyBase; // IMPORTANTE
import io.github.guilhermegodoydev.greenhorizon.core.entities.projectiles.Projectile; // IMPORTANTE
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

    // Corrigido: Removido o attack() antigo e mantido apenas o que segue a assinatura da TowerBase
    @Override
    public void attack(EnemyBase target, Array<Projectile> projectiles) {
        // Criamos o sprite do projétil (sua folha diagonal)
        Sprite leafSprite = new Sprite(Assets.getTexture("tiro_folha.png"));

        // Adicionamos o novo projétil à lista para que o TowerManager o atualize
        projectiles.add(new Projectile(leafSprite, position.x, position.y, damage, target));

        System.out.println("TowerTree disparou folha contra: " + target.getClass().getSimpleName());
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
    }
}
