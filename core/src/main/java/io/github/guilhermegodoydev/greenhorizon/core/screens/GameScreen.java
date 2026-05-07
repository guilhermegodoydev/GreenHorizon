package io.github.guilhermegodoydev.greenhorizon.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.guilhermegodoydev.greenhorizon.Main;
import io.github.guilhermegodoydev.greenhorizon.core.entities.towers.TowerBase;
import io.github.guilhermegodoydev.greenhorizon.core.entities.towers.TowerTree;
import io.github.guilhermegodoydev.greenhorizon.core.events.GameEvent;
import io.github.guilhermegodoydev.greenhorizon.core.events.GameEventListener;
import io.github.guilhermegodoydev.greenhorizon.core.input.InputHandler;
import io.github.guilhermegodoydev.greenhorizon.core.managers.EnemyManager;
import io.github.guilhermegodoydev.greenhorizon.core.managers.ManagerUI;
import io.github.guilhermegodoydev.greenhorizon.core.managers.SettingsManager;
import io.github.guilhermegodoydev.greenhorizon.core.managers.TowerManager;
import io.github.guilhermegodoydev.greenhorizon.core.managers.WaveManager;
import io.github.guilhermegodoydev.greenhorizon.core.map.MapHandler;
import io.github.guilhermegodoydev.greenhorizon.core.map.TowerSlot;
import io.github.guilhermegodoydev.greenhorizon.core.itens.LifeManager;
import io.github.guilhermegodoydev.greenhorizon.core.itens.CoinsManager;
import io.github.guilhermegodoydev.greenhorizon.core.utils.Assets;

public class GameScreen extends BaseScreen implements GameEventListener {
    private final MapHandler mapHandler;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private final ManagerUI managerUI;
    private final TowerManager towerManager;
    private final LifeManager lifeManager;
    private final CoinsManager coinsManager;
    private boolean paused = false;
    private InputMultiplexer multiplexer;
    private EnemyManager enemyManager;
    private WaveManager waveManager;

    public GameScreen(Main game) {
        super(game);

        lifeManager = new LifeManager(10);
        coinsManager = new CoinsManager(500);
        mapHandler = new MapHandler("mapa.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(mapHandler.getTiledMap());
        towerManager = new TowerManager();

        enemyManager = new EnemyManager(mapHandler.getWaypoints(), lifeManager, coinsManager);
        waveManager = new WaveManager(enemyManager);

        this.managerUI = new ManagerUI(viewport, game.batch, lifeManager, coinsManager, this, this, waveManager);

        // Passando "this" (a própria GameScreen) para o InputHandler
        InputHandler inputHandler = new InputHandler(viewport, mapHandler, managerUI, towerManager, this);

        multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(managerUI.getStage());
        multiplexer.addProcessor(inputHandler);

        Gdx.input.setInputProcessor(multiplexer);

        // Solicita a transição para a música do jogo
        game.fadeToMusic("sfx/bgm.mp3");
    }

    @Override
    public void show() {
        super.show();
        Gdx.input.setInputProcessor(multiplexer);
    }

    public void togglePause() {
        this.paused = !this.paused;
        managerUI.setPauseVisible(paused);
    }

    // Novo getter para o InputHandler conseguir consultar o estado do jogo
    public boolean isPaused() {
        return paused;
    }

    @Override
    public void onEvent(GameEvent event) {
        switch (event.type) {
            case BUILD_TOWER:
                Object[] data = (Object[]) event.data;
                TowerSlot slot = (TowerSlot) data[0];
                String tipo = (String) data[1];

                int custo = tipo.equalsIgnoreCase("Arvore") ? TowerTree.CUSTO : 100;
                towerManager.buildTower(slot, tipo, custo, coinsManager);

                Assets.getSound("sfx/plant.wav").play(SettingsManager.getSfxVolume());
                break;

            case SELL_TOWER:
                if (event.data instanceof TowerBase) {
                    TowerBase torre = (TowerBase) event.data;
                    coinsManager.acrescentar(torre.getValorVenda());
                    towerManager.sellTower(torre);
                }
                break;

            case UPGRADE_TOWER:
                if (event.data instanceof TowerBase) {
                    TowerBase torre = (TowerBase) event.data;
                    towerManager.upgradeTower(torre, coinsManager);
                }
                break;
        }
    }

    public Main getGame() {
        return game;
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        if (lifeManager.getVidasAtuais() <= 0) {
            // Gatilho do silêncio chamado ANTES de mudar a tela
            game.fadeToMusic(null);

            game.setScreen(new GameOverScreen(game));
            this.dispose();
            return;
        }

        if (!paused) {
            camera.update();
            towerManager.update(delta, enemyManager.getEnemies());
            waveManager.update(delta);
            enemyManager.update(delta);
        }

        mapRenderer.setView(camera);
        mapRenderer.render();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        mapHandler.renderSlots(game.batch);
        towerManager.render(game.batch);
        enemyManager.render(game.batch);
        game.batch.end();

        managerUI.render(delta);
    }

    @Override
    public void dispose() {
        mapHandler.dispose();
        mapRenderer.dispose();
        managerUI.dispose();
    }
}
