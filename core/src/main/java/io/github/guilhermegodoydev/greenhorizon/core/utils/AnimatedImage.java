package io.github.guilhermegodoydev.greenhorizon.core.utils; // Ajuste o package se necessário

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
        // O Stage chama o act() de todos os atores a cada frame.
        // É aqui que atualizamos o tempo da animação.
        super.act(delta);

        if (!animation.isAnimationFinished(stateTime)) {
            stateTime += delta;
        }

        // Pega o frame correto para o tempo atual
        TextureRegion currentFrame = animation.getKeyFrame(stateTime, false); // true = em loop

        // Atualiza o Drawable da classe Image super
        ((TextureRegionDrawable) getDrawable()).setRegion(currentFrame);
    }
}
