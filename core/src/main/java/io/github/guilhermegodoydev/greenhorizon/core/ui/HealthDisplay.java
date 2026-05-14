package io.github.guilhermegodoydev.greenhorizon.core.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import io.github.guilhermegodoydev.greenhorizon.core.utils.Assets;
import io.github.guilhermegodoydev.greenhorizon.core.itens.LifeManager;

public class HealthDisplay extends Actor {
    private final Texture barTexture;
    private final TextureRegion[] heartFrames;
    private final BitmapFont font;
    private final LifeManager lifeManager;

    private float animationTime = 0;
    private boolean isTakingDamage = false;
    private int previousLives;

    public HealthDisplay(LifeManager lifeManager, float x, float y) {
        this.lifeManager = lifeManager;
        this.previousLives = lifeManager.getCurrentLives();

        this.barTexture = Assets.getTexture("apenas_barra.png");
        Texture spriteSheet = Assets.getTexture("animacao_perdendo_vida.png");

        this.font = new BitmapFont();
        this.font.getData().setScale(1.0f);

        int frameWidth = spriteSheet.getWidth() / 4;
        int frameHeight = spriteSheet.getHeight();
        this.heartFrames = new TextureRegion[4];
        for (int i = 0; i < 4; i++) {
            heartFrames[i] = new TextureRegion(spriteSheet, i * frameWidth, 0, frameWidth, frameHeight);
        }

        setSize(60, 25);
        setPosition(x, y);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (lifeManager.getCurrentLives() < previousLives) {
            isTakingDamage = true;
            animationTime = 0;
            previousLives = lifeManager.getCurrentLives();
        }

        if (isTakingDamage) {
            animationTime += delta;
            if ((int)(animationTime / 0.1f) > 3) {
                isTakingDamage = false;
                animationTime = 0;
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        float x = getX();
        float y = getY();
        float scaleX = getScaleX();
        float scaleY = getScaleY();

        batch.draw(barTexture, x, y, getWidth() * scaleX, getHeight() * scaleY);

        int currentFrame = isTakingDamage ? (int)(animationTime / 0.1f) : 0;
        batch.draw(heartFrames[currentFrame], x + (5 * scaleX), y + (6 * scaleY), 16 * scaleX, 16 * scaleY);

        font.getData().setScale(scaleX);
        font.draw(batch, "X " + lifeManager.getCurrentLives(), x + (25 * scaleX), y + (18 * scaleY));
    }

    public void dispose() {
        font.dispose();
    }
}
