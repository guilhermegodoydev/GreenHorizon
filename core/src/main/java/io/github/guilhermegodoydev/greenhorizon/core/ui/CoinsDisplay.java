package io.github.guilhermegodoydev.greenhorizon.core.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import io.github.guilhermegodoydev.greenhorizon.core.utils.Assets;
import io.github.guilhermegodoydev.greenhorizon.core.itens.CoinsManager;

public class CoinsDisplay extends Actor {
    private final Texture imgBarra;
    private final Texture imgMoeda;
    private final BitmapFont fonte;
    private final CoinsManager coinsManager;

    public CoinsDisplay(CoinsManager coinsManager, float x, float y) {
        this.coinsManager = coinsManager;

        this.imgBarra = Assets.getTexture("apenas_barra.png");
        this.imgMoeda = Assets.getTexture("moeda.png");

        this.fonte = new BitmapFont();

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
        float scaleX = getScaleX();
        float scaleY = getScaleY();

        // Desenha a barra de fundo multiplicando pela escala (0.7f)
        batch.draw(imgBarra, x, y, getWidth() * scaleX, getHeight() * scaleY);

        // Desenha o ícone da moeda também escalado
        batch.draw(imgMoeda, x + (5 * scaleX), y + (4 * scaleY), 16 * scaleX, 16 * scaleY);

        // Ajusta o tamanho da fonte dinamicamente de acordo com a escala e reposiciona
        fonte.getData().setScale(scaleX);
        fonte.draw(batch, "X " + coinsManager.getSaldoAtual(), x + (25 * scaleX), y + (18 * scaleY));
    }

    public void dispose() {
        fonte.dispose();
    }
}
