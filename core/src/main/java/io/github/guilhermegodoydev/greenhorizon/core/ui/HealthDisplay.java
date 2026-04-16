package io.github.guilhermegodoydev.greenhorizon.core.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import io.github.guilhermegodoydev.greenhorizon.core.managers.LifeManager;

public class HealthDisplay extends Actor {
    private final Texture imgBarra;
    private final Texture spriteSheetAnimacao;
    private final TextureRegion[] framesCoracao;
    private final BitmapFont fonte;
    private final LifeManager lifeManager;

    private float tempoAnimacao = 0;
    private boolean estaSofrendoDano = false;
    private int vidasAnteriores;

    public HealthDisplay(LifeManager lifeManager) {
        this.lifeManager = lifeManager;
        this.vidasAnteriores = lifeManager.getVidasAtuais();

        imgBarra = new Texture("apenas_barra.png");
        spriteSheetAnimacao = new Texture("animacao_perdendo_vida.png");
        fonte = new BitmapFont();

        imgBarra.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        spriteSheetAnimacao.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        int larguraFrame = spriteSheetAnimacao.getWidth() / 4;
        int alturaFrame = spriteSheetAnimacao.getHeight();
        framesCoracao = new TextureRegion[4];
        for (int i = 0; i < 4; i++) {
            framesCoracao[i] = new TextureRegion(spriteSheetAnimacao, i * larguraFrame, 0, larguraFrame, alturaFrame);
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
        imgBarra.dispose();
        spriteSheetAnimacao.dispose();
        fonte.dispose();
    }
}
