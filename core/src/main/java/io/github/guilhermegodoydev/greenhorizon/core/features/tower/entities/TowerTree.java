package io.github.guilhermegodoydev.greenhorizon.core.features.tower.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import io.github.guilhermegodoydev.greenhorizon.core.features.enemy.entities.EnemyBase;
import io.github.guilhermegodoydev.greenhorizon.core.map.TowerSlot;
import io.github.guilhermegodoydev.greenhorizon.core.utils.Assets;

public class TowerTree extends AttackTower {
    public static final int COST = 50;

    public TowerTree(float x, float y, TowerSlot slot) {
        super(
            new Sprite(Assets.getTexture("torre_arvore_nivel1.png")),
            x, y, 10f, 80f, 1.2f, slot
        );
    }

    @Override
    public void attack(EnemyBase target, Array<Projectile> projectiles) {
        Sprite leafSprite = new Sprite(Assets.getTexture("tiro_folha.png"));
        projectiles.add(new Projectile(leafSprite, getPosition().x, getPosition().y, getDamage(), target));
    }

    @Override
    public int getUpgradeCost() {
        if (getLevel() == 1) return 50;
        if (getLevel() == 2) return 100;
        return 9999;
    }

    @Override
    public void applyStatusUpgrade() {
        setRange(getRange() + 10f);

        if (getLevel() == 2) {
            setDamage(15f);
            getSprite().setRegion(Assets.getTexture("torre_arvore_nivel2.png"));
        } else if (getLevel() == 3) {
            setDamage(25f);
            getSprite().setRegion(Assets.getTexture("torre_arvore_nivel3.png"));
        }

        getSprite().setSize(getSprite().getRegionWidth(), getSprite().getRegionHeight());
    }
}
