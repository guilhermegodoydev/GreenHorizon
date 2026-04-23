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
import io.github.guilhermegodoydev.greenhorizon.core.itens.LifeManager;

// 1. IMPORTAÇÕES DAS MOEDAS
import io.github.guilhermegodoydev.greenhorizon.core.ui.CoinsDisplay;
import io.github.guilhermegodoydev.greenhorizon.core.itens.CoinsManager;

public class ManagerUI implements TowerSelectionListener, Disposable {
    private final Stage stage;
    private final HealthDisplay healthDisplay;
    private final CoinsDisplay coinsDisplay; // 2. NOVO ATRIBUTO DAS MOEDAS
    private final ConstructionMenu constructionMenu;
    private final TowerActionMenu actionMenu;
    private final GameEventListener eventListener;

    private TowerSlot slotAlvo;
    private TowerBase torreSelecionada;

    // 3. CONSTRUTOR ATUALIZADO PARA RECEBER O CoinsManager
    public ManagerUI(Viewport viewport, SpriteBatch batch, LifeManager lifeManager, CoinsManager coinsManager, GameEventListener listener) {
        this.stage = new Stage(viewport, batch);
        this.eventListener = listener;

        this.healthDisplay = new HealthDisplay(lifeManager);
        this.coinsDisplay = new CoinsDisplay(coinsManager); // 4. INSTANCIANDO AS MOEDAS

        this.actionMenu = new TowerActionMenu(this);
        this.constructionMenu = new ConstructionMenu(this);

        stage.addActor(healthDisplay);
        stage.addActor(coinsDisplay); // 5. ADICIONANDO AS MOEDAS NA TELA
        stage.addActor(constructionMenu);
        stage.addActor(actionMenu);

        // 6. DEFININDO AS POSIÇÕES
        healthDisplay.setPosition(20, viewport.getWorldHeight() - 30);
        coinsDisplay.setPosition(20, viewport.getWorldHeight() - 60);
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

        Utils.setCenteredPosition(actionMenu, torre.getPosition().x, torre.getPosition().y + 8);

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
        healthDisplay.dispose(); // Limpa as fontes da vida da memória
        coinsDisplay.dispose();  // Limpa as fontes das moedas da memória
        stage.dispose();
    }

    public boolean isVisivel() {
        return constructionMenu.isVisible() || actionMenu.isVisible();
    }

    public Stage getStage() { return stage; }
}
