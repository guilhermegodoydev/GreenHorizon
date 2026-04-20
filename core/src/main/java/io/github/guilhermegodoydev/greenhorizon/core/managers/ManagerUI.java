package io.github.guilhermegodoydev.greenhorizon.core.managers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.guilhermegodoydev.greenhorizon.core.entities.towers.TowerBase;
import io.github.guilhermegodoydev.greenhorizon.core.events.GameEvent;
import io.github.guilhermegodoydev.greenhorizon.core.events.GameEventListener;
import io.github.guilhermegodoydev.greenhorizon.core.map.TowerSlot;
import io.github.guilhermegodoydev.greenhorizon.core.ui.menu.ConstructionMenu;
import io.github.guilhermegodoydev.greenhorizon.core.ui.HealthDisplay;
import io.github.guilhermegodoydev.greenhorizon.core.ui.menu.TowerActionMenu;
import io.github.guilhermegodoydev.greenhorizon.core.ui.TowerSelectionListener;
import io.github.guilhermegodoydev.greenhorizon.core.utils.Utils;
import io.github.guilhermegodoydev.greenhorizon.itens.LifeManager;

public class ManagerUI implements TowerSelectionListener, Disposable {
    private final Stage stage;
    private final HealthDisplay healthDisplay;
    private final ConstructionMenu constructionMenu;
    private final TowerActionMenu actionMenu;
    private final GameEventListener eventListener;

    private TowerSlot slotAlvo;
    private TowerBase torreSelecionada;

    public ManagerUI(Viewport viewport, SpriteBatch batch, LifeManager lifeManager, GameEventListener listener) {
        this.stage = new Stage(viewport, batch);
        this.eventListener = listener;

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
            eventListener.onEvent(new GameEvent(GameEvent.Type.SELL_TOWER, torreSelecionada));
        } else if (tipo.equals("Upgrade")) {
            eventListener.onEvent(new GameEvent(GameEvent.Type.UPGRADE_TOWER, torreSelecionada));
        } else {
            Object[] dadosConstrucao = { slotAlvo, tipo };
            eventListener.onEvent(new GameEvent(GameEvent.Type.BUILD_TOWER, dadosConstrucao));
        }
        fecharTodosMenus();
    }

    public void abrirMenuAcao(TowerBase torre) {
        this.torreSelecionada = torre;
        fecharTodosMenus();

        Utils.setCenteredPosition(actionMenu, torre.getPosition().x, torre.getPosition().y);

        actionMenu.setVisible(true);
    }

    public void abrirMenu(TowerSlot slot) {
        this.slotAlvo = slot;
        fecharTodosMenus();

        Utils.setCenteredPosition(constructionMenu, slot.getCenterX(), slot.getBounds().y + slot.getBounds().height);

        constructionMenu.setVisible(true);
    }

    public void fecharTodosMenus() {
        constructionMenu.setVisible(false);
        actionMenu.setVisible(false);
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
        return constructionMenu.isVisible() || actionMenu.isVisible();
    }

    public Stage getStage() { return stage; }
}
