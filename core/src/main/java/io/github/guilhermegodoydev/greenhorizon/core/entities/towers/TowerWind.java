package io.github.guilhermegodoydev.greenhorizon.core.entities.towers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import io.github.guilhermegodoydev.greenhorizon.core.entities.enemies.EnemyBase;
import io.github.guilhermegodoydev.greenhorizon.core.entities.projectiles.Projectile;
import io.github.guilhermegodoydev.greenhorizon.core.map.TowerSlot;
import io.github.guilhermegodoydev.greenhorizon.core.utils.Assets;
import io.github.guilhermegodoydev.greenhorizon.core.itens.CoinsManager;

public class TowerWind extends TowerBase {
    public static final int CUSTO = 100;
    private float slowFactor = 0.6f; // Deixa o inimigo com 60% da velocidade (40% mais lento)

    public TowerWind(float x, float y, TowerSlot slot) {
        super(
            new Sprite(Assets.getTexture("torre_arvore_nivel1.png")),
            x, y, 0f,
            120f, // Alcance grande!
            0f, slot
        );
        this.valorVenda = 50;
    }

    @Override
    public boolean isAtacante() { return false; }

    @Override
    public void update(float delta, Array<EnemyBase> enemies, Array<Projectile> projectiles, CoinsManager coinsManager) {
        for (EnemyBase enemy : enemies) {
            if (enemy.isActive() && position.dst(enemy.getPosition()) <= range) {
                enemy.applySlow(slowFactor);
            }
        }
    }

    @Override
    public void attack(EnemyBase target, Array<Projectile> projectiles) {}

    @Override
    public int getCustoUpgrade() { return (nivel == 1) ? 60 : 100; }

    @Override
    public void aplicarMelhoriaStatus() {
        if (nivel == 2) {
            this.slowFactor = 0.45f;
            this.range += 20f;
        } else if (nivel == 3) {
            this.slowFactor = 0.3f;
            this.range += 30f;
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.setColor(Color.CYAN); // Grayboxing azul para Vento/Gelo
        super.render(batch);
        batch.setColor(Color.WHITE);
    }
}
