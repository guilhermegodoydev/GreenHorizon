package io.github.guilhermegodoydev.greenhorizon.core.utils;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.math.Vector2;

public class Utils {

    public static void setCenteredPosition(Actor actor, float targetX, float targetY) {
        float centerX = targetX - (actor.getWidth() / 2f);
        actor.setPosition(centerX, targetY);
    }

    public static float getDistance(float x1, float y1, float x2, float y2) {
        return Vector2.dst(x1, y1, x2, y2);
    }
}
