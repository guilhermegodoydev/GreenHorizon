package io.github.guilhermegodoydev.greenhorizon.core.utils;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Um Actor do Scene2D que exibe uma animação de Sprite Sheet.
 */
public class AnimatedImage extends Image {
    private final Animation<TextureRegion> animation;
    private float stateTime;

    public AnimatedImage(Animation<TextureRegion> animation) {
        // Inicializa a Image super com o primeiro frame da animação
        super(animation.getKeyFrame(0));
        this.animation = animation;
        this.stateTime = 0f;
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        // Adicionamos o delta ao tempo contínuo sempre
        stateTime += delta;

        // Pega o frame correto para o tempo atual, respeitando o PlayMode configurado na animação!
        TextureRegion currentFrame = animation.getKeyFrame(stateTime);

        // Atualiza o Drawable da classe Image super
        ((TextureRegionDrawable) getDrawable()).setRegion(currentFrame);
    }
}
