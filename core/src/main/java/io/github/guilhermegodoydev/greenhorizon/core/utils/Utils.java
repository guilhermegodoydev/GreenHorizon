package io.github.guilhermegodoydev.greenhorizon.core.utils;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.math.Vector2;

public class Utils {

    /**
     * Centraliza um Ator do Scene2D (Menu, Imagem) horizontalmente em relação a um ponto X.
     * @param actor O elemento da UI (ex: ConstructionMenu)
     * @param targetX O ponto central desejado (ex: slot.getCenterX())
     * @param targetY A base de onde ele deve começar (ex: topo do slot)
     */
    public static void setCenteredPosition(Actor actor, float targetX, float targetY) {
        float centerX = targetX - (actor.getWidth() / 2f);
        actor.setPosition(centerX, targetY);
    }

    /**
     * Calcula a distância entre dois pontos (útil para torres verificarem alcance depois)
     */
    public static float getDistance(float x1, float y1, float x2, float y2) {
        return Vector2.dst(x1, y1, x2, y2);
    }
}
