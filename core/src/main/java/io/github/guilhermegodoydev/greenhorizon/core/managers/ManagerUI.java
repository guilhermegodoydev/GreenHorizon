package io.github.guilhermegodoydev.greenhorizon.core.managers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.guilhermegodoydev.greenhorizon.core.map.TowerSlot;
import io.github.guilhermegodoydev.greenhorizon.core.ui.ConstructionMenu;
import io.github.guilhermegodoydev.greenhorizon.core.ui.HealthDisplay;
import io.github.guilhermegodoydev.greenhorizon.core.ui.TowerSelectionListener;
import io.github.guilhermegodoydev.greenhorizon.itens.LifeManager;

public class ManagerUI implements TowerSelectionListener,Disposable {
    private final Stage stage;
    private final HealthDisplay healthDisplay;
    private final ConstructionMenu constructionMenu;
    private TowerManager towerManager;
    private TowerSlot slotAlvo;

    public ManagerUI(Viewport viewport, SpriteBatch batch, LifeManager lifeManager, TowerManager towerManager) {
        this.stage = new Stage(viewport, batch);
        this.towerManager = towerManager;
        this.healthDisplay = new HealthDisplay(lifeManager);

        this.constructionMenu = new ConstructionMenu(this);


        stage.addActor(healthDisplay);
        stage.addActor(constructionMenu);
    }

    @Override
    public void onTowerSelected(String tipo) {
        if (slotAlvo != null) {
            towerManager.buildTower(slotAlvo, tipo);
        }
    }

    public void abrirMenu(TowerSlot slot) {
        this.slotAlvo = slot;

        float xBase = slot.getCenterX() - (constructionMenu.getWidth() / 2f);
        float yBase = slot.getBounds().y + slot.getBounds().height ;

        constructionMenu.setPosition(xBase, yBase);
        constructionMenu.setVisible(true);
    }

    public void confirmarConstrucao(String tipo) {
        if (slotAlvo != null) {
            towerManager.buildTower(slotAlvo, tipo);
        }
    }

    public void fecharMenu() {
        constructionMenu.setVisible(false);
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
        return constructionMenu.isVisible();
    }

    public Stage getStage() { return stage; }
}
