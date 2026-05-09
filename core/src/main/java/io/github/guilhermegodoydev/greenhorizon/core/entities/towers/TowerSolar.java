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

public class TowerSolar extends TowerBase {
    public static final int CUSTO = 150;

    // Atributos de geração financeira
    private float productionTimer = 0f;
    private float productionInterval = 10f; // A cada 10 segundos
    private int moneyAmount = 15; // Gera $15

    public TowerSolar(float x, float y, TowerSlot slot) {
        super(
            new Sprite(Assets.getTexture("torre_arvore_nivel1.png")), // Placeholder
            x,
            y,
            0f, // Sem dano
            0f, // Sem alcance
            0f, // Sem fireRate
            slot
        );
        this.valorVenda = 75; // Preço de revenda
    }

    // A MÁGICA: Informa ao sistema que essa torre não atira
    @Override
    public boolean isAtacante() {
        return false;
    }

    // Sobrescreve o update para gerar dinheiro em vez de procurar alvos
    @Override
    public void update(float delta, Array<EnemyBase> enemies, Array<Projectile> projectiles, CoinsManager coinsManager) {
        productionTimer += delta;
        if (productionTimer >= productionInterval) {
            coinsManager.acrescentar(moneyAmount);
            productionTimer = 0f;
            System.out.println("Painel Solar gerou $" + moneyAmount + "!");
            // Aqui você pode tocar um somzinho de dinheiro caindo futuramente
        }
    }

    // Método obrigatório, mas que nunca será chamado graças ao isAtacante()
    @Override
    public void attack(EnemyBase target, Array<Projectile> projectiles) { }

    @Override
    public int getCustoUpgrade() {
        if (nivel == 1) return 80;
        if (nivel == 2) return 120;
        return 9999;
    }

    @Override
    public void aplicarMelhoriaStatus() {
        // Ao subir de nível, ela gera mais dinheiro ou gera mais rápido
        if (this.nivel == 2) {
            this.moneyAmount = 25;
            this.productionInterval = 8f;
        } else if (this.nivel == 3) {
            this.moneyAmount = 50;
            this.productionInterval = 6f;
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        // GRAYBOXING: Pinta a torre de amarelo para diferenciar da Árvore
        batch.setColor(Color.YELLOW);
        super.render(batch);
        batch.setColor(Color.WHITE); // Reseta a cor
    }
}
