package io.github.guilhermegodoydev.greenhorizon.core.managers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class ManagerUI {
    private Texture texturaMenu;
    private Vector2 posicaoMenu;
    private boolean visivel;

    public ManagerUI() {
        texturaMenu = new Texture("menu_construcao.png");
        posicaoMenu = new Vector2();
        visivel = false;
    }

    public void abrirMenu(MapObject slot) {
        if (slot instanceof RectangleMapObject) {
            Rectangle rect = ((RectangleMapObject) slot).getRectangle();

            float xCentral = rect.x + (rect.width / 2f);
            float yTopo = rect.y + rect.height;

            this.posicaoMenu.set(xCentral, yTopo);
            this.visivel = true;
        }
    }

    public void fecharMenu() {
        this.visivel = false;
    }

    public void render(SpriteBatch batch) {
        if (visivel) {
            float drawX = posicaoMenu.x - (texturaMenu.getWidth() / 2f);
            float drawY = posicaoMenu.y;

            batch.draw(texturaMenu, drawX, drawY);
        }
    }

    public void dispose() {
        texturaMenu.dispose();
    }

    public boolean isVisivel() { return visivel; }
}
