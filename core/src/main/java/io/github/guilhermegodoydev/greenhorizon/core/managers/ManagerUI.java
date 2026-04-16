package io.github.guilhermegodoydev.greenhorizon.core.managers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.guilhermegodoydev.greenhorizon.core.map.TowerSlot;
import io.github.guilhermegodoydev.greenhorizon.core.ui.HealthDisplay;
import io.github.guilhermegodoydev.greenhorizon.itens.LifeManager;

public class ManagerUI implements Disposable {
    private Stage stage;
    private Image menuConstrucao;
    private HealthDisplay healthDisplay;

    public ManagerUI(Viewport viewport, SpriteBatch batch, LifeManager lifeManager) {
        this.stage = new Stage(viewport, batch);

        this.healthDisplay = new HealthDisplay(lifeManager);
        healthDisplay.setPosition(250, 210);
        stage.addActor(healthDisplay);

        menuConstrucao = new Image(new com.badlogic.gdx.graphics.Texture("menu_construcao.png"));
        menuConstrucao.setVisible(false);
        stage.addActor(menuConstrucao);
    }

    public void abrirMenu(TowerSlot slot) {
        float xCentral = slot.getCenterX() - (menuConstrucao.getWidth() / 2f);
        float yTopo = slot.getBounds().y + slot.getBounds().height;

        menuConstrucao.setPosition(xCentral, yTopo);
        menuConstrucao.setVisible(true);
    }

    public void fecharMenu() {
        menuConstrucao.setVisible(false);
    }

    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public boolean isVisivel() {
        return menuConstrucao.isVisible();
    }

    public Stage getStage() { return stage; }
}
