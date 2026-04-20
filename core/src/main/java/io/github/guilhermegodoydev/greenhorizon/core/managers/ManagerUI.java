package io.github.guilhermegodoydev.greenhorizon.core.managers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.guilhermegodoydev.greenhorizon.core.entities.towers.TowerBase;
import io.github.guilhermegodoydev.greenhorizon.core.map.TowerSlot;
import io.github.guilhermegodoydev.greenhorizon.core.ui.ConstructionMenu;
import io.github.guilhermegodoydev.greenhorizon.core.ui.HealthDisplay;
import io.github.guilhermegodoydev.greenhorizon.core.ui.TowerActionMenu;
import io.github.guilhermegodoydev.greenhorizon.core.ui.TowerSelectionListener;
import io.github.guilhermegodoydev.greenhorizon.itens.LifeManager;

public class ManagerUI implements TowerSelectionListener,Disposable {
    private final Stage stage;
    private final HealthDisplay healthDisplay;
    private final ConstructionMenu constructionMenu;
    private TowerManager towerManager;
    private TowerSlot slotAlvo;
    private final TowerActionMenu actionMenu;
    private TowerBase torreSelecionada;

    public ManagerUI(Viewport viewport, SpriteBatch batch, LifeManager lifeManager, TowerManager towerManager) {
        this.stage = new Stage(viewport, batch);
        this.towerManager = towerManager;
        this.healthDisplay = new HealthDisplay(lifeManager);
        this.actionMenu = new TowerActionMenu(this);

        this.constructionMenu = new ConstructionMenu(this);


        stage.addActor(healthDisplay);
        stage.addActor(constructionMenu);
        stage.addActor(actionMenu);
    }

    @Override
    public void onTowerSelected(String tipo) {
        if (tipo.equals("Vender")) {
            System.out.println("Vendendo torre...");
            // Lógica para remover do towerManager e liberar o slot
        } else if (tipo.equals("Upgrade")) {
            System.out.println("Fazendo upgrade...");
        } else {
            // Lógica de construção que já tínhamos
            towerManager.buildTower(slotAlvo, tipo);
        }
    }

    public void abrirMenuAcao(TowerBase torre) {
        this.torreSelecionada = torre;

        constructionMenu.setVisible(false);

        float xBase = torre.getPosition().x - (actionMenu.getWidth() / 2f);
        float yBase = torre.getPosition().y;

        actionMenu.setPosition(xBase, yBase);
        actionMenu.setVisible(true);

        constructionMenu.setVisible(false);
    }

    public void abrirMenu(TowerSlot slot) {
        this.slotAlvo = slot;

        actionMenu.setVisible(false);

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

    public void fecharTodosMenus() {
        constructionMenu.setVisible(false);
        actionMenu.setVisible(false);
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
