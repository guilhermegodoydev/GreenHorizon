package io.github.guilhermegodoydev.greenhorizon.core.utils;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class AnimatedImage extends Image {
    private final Animation<TextureRegion> animation;
    private float stateTime;

    public AnimatedImage(Animation<TextureRegion> animation) {
        super(animation.getKeyFrame(0));
        this.animation = animation;
        this.stateTime = 0f;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        stateTime += delta;
        TextureRegion currentFrame = animation.getKeyFrame(stateTime);
        ((TextureRegionDrawable) getDrawable()).setRegion(currentFrame);
    }
}
