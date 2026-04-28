package io.github.guilhermegodoydev.greenhorizon.core.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import io.github.guilhermegodoydev.greenhorizon.core.utils.Assets;
import io.github.guilhermegodoydev.greenhorizon.core.itens.CoinsManager;

public class CoinsDisplay extends Actor {
    private final Texture imgBarra;
    private final Texture imgMoeda; // 1. NOVA VARIÁVEL PARA A MOEDA
    private final BitmapFont fonte;
    private final CoinsManager coinsManager;

    public CoinsDisplay(CoinsManager coinsManager, float x, float y) {
        this.coinsManager = coinsManager;

        // Carrega as imagens
        this.imgBarra = Assets.getTexture("apenas_barra.png");
        this.imgMoeda = Assets.getTexture("moeda.png"); // 2. CARREGA A SUA IMAGEM NOVA

        this.fonte = new BitmapFont();

        // Tamanho da HUD (largura, altura)
        setSize(80, 25);

        setPosition(x,y);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        float x = getX();
        float y = getY();

        // 1º: Desenha a barra de fundo
        batch.draw(imgBarra, x, y, getWidth(), getHeight());

        // 2º: Desenha o ícone da moeda (Ajuste o tamanho 16x16 se ficar muito grande/pequena)
        batch.draw(imgMoeda, x + 5, y + 4, 16, 16);

        // 3º: Desenha o texto.
        // Tirei a palavra "Moedas: " e coloquei "X " igual a barra de vida.
        // O X + 25 empurra o texto pra direita pra não ficar em cima da moeda.
        fonte.draw(batch, "X " + coinsManager.getSaldoAtual(), x + 25, y + 18);
    }

    public void dispose() {
        fonte.dispose();
    }
}
