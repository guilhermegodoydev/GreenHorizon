package io.github.guilhermegodoydev.greenhorizon.core.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import io.github.guilhermegodoydev.greenhorizon.core.utils.Assets;
import io.github.guilhermegodoydev.greenhorizon.core.itens.LifeManager;

public class HealthDisplay extends Actor {
    private final Texture imgBarra;
    private final TextureRegion[] framesCoracao;
    private final BitmapFont fonte;
    private final LifeManager lifeManager;

    private float tempoAnimacao = 0;
    private boolean estaSofrendoDano = false;
    private int vidasAnteriores;

    public HealthDisplay(LifeManager lifeManager) {
        this.lifeManager = lifeManager;
        this.vidasAnteriores = lifeManager.getVidasAtuais();

        this.imgBarra = Assets.getTexture("apenas_barra.png");
        Texture spriteSheet = Assets.getTexture("animacao_perdendo_vida.png");

        this.fonte = new BitmapFont();

        int larguraFrame = spriteSheet.getWidth() / 4;
        int alturaFrame = spriteSheet.getHeight();
        this.framesCoracao = new TextureRegion[4];
        for (int i = 0; i < 4; i++) {
            framesCoracao[i] = new TextureRegion(spriteSheet, i * larguraFrame, 0, larguraFrame, alturaFrame);
        }

        setSize(60, 25);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (lifeManager.getVidasAtuais() < vidasAnteriores) {
            estaSofrendoDano = true;
            tempoAnimacao = 0;
            vidasAnteriores = lifeManager.getVidasAtuais();
        }

        if (estaSofrendoDano) {
            tempoAnimacao += delta;
            if ((int)(tempoAnimacao / 0.1f) > 3) {
                estaSofrendoDano = false;
                tempoAnimacao = 0;
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        float x = getX();
        float y = getY();

        batch.draw(imgBarra, x, y, getWidth(), getHeight());

        int frameAtual = estaSofrendoDano ? (int)(tempoAnimacao / 0.1f) : 0;
        batch.draw(framesCoracao[frameAtual], x + 5, y + 6, 16, 16);

        fonte.draw(batch, "X " + lifeManager.getVidasAtuais(), x + 25, y + 18);
    }

    public void dispose() {
        fonte.dispose();
    }
}
