package io.github.guilhermegodoydev.greenhorizon.core.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import io.github.guilhermegodoydev.greenhorizon.core.utils.Assets;
import io.github.guilhermegodoydev.greenhorizon.core.itens.CoinsManager;

public class CoinsDisplay extends Actor {
    private final Texture barTexture;
    private final Texture coinTexture;
    private final BitmapFont font;
    private final CoinsManager coinsManager;

    public CoinsDisplay(CoinsManager coinsManager, float x, float y) {
        this.coinsManager = coinsManager;

        this.barTexture = Assets.getTexture("apenas_barra.png");
        this.coinTexture = Assets.getTexture("moeda.png");

        this.font = new BitmapFont();

        setSize(80, 25);
        setPosition(x, y);
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

        batch.draw(barTexture, x, y, getWidth() * scaleX, getHeight() * scaleY);
        batch.draw(coinTexture, x + (5 * scaleX), y + (4 * scaleY), 16 * scaleX, 16 * scaleY);

        font.getData().setScale(scaleX);
        font.draw(batch, "X " + coinsManager.getCurrentBalance(), x + (25 * scaleX), y + (18 * scaleY));
    }

    public void dispose() {
        font.dispose();
    }
}
