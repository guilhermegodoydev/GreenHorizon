package io.github.guilhermegodoydev.greenhorizon.core.utils;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class Utils {

    public static void setCenteredPosition(Actor actor, float targetX, float targetY) {
        float centerX = targetX - (actor.getWidth() / 2f);
        actor.setPosition(centerX, targetY);
    }

}
