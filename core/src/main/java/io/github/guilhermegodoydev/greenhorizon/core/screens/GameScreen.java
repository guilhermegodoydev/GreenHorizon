package io.github.guilhermegodoydev.greenhorizon.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.guilhermegodoydev.greenhorizon.Main;
import io.github.guilhermegodoydev.greenhorizon.core.features.tower.entities.TowerBase;
import io.github.guilhermegodoydev.greenhorizon.core.features.tower.entities.TowerSolar;
import io.github.guilhermegodoydev.greenhorizon.core.features.tower.entities.TowerWind;
import io.github.guilhermegodoydev.greenhorizon.core.features.tower.entities.TowerTree;
import io.github.guilhermegodoydev.greenhorizon.core.events.GameEvent;
import io.github.guilhermegodoydev.greenhorizon.core.events.GameEventListener;
import io.github.guilhermegodoydev.greenhorizon.core.input.InputHandler;
import io.github.guilhermegodoydev.greenhorizon.core.features.enemy.EnemyManager;
import io.github.guilhermegodoydev.greenhorizon.core.ui.ManagerUI;
import io.github.guilhermegodoydev.greenhorizon.core.utils.SettingsManager;
import io.github.guilhermegodoydev.greenhorizon.core.features.tower.TowerManager;
import io.github.guilhermegodoydev.greenhorizon.core.features.enemy.WaveManager;
import io.github.guilhermegodoydev.greenhorizon.core.map.MapHandler;
import io.github.guilhermegodoydev.greenhorizon.core.map.TowerSlot;
import io.github.guilhermegodoydev.greenhorizon.core.features.economy.LifeManager;
import io.github.guilhermegodoydev.greenhorizon.core.features.economy.CoinsManager;
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
        coinsManager = new CoinsManager(200);
        mapHandler = new MapHandler("mapa.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(mapHandler.getTiledMap());
        towerManager = new TowerManager();

        enemyManager = new EnemyManager(mapHandler.getWaypoints(), lifeManager, coinsManager);
        waveManager = new WaveManager(enemyManager);

        this.managerUI = new ManagerUI(viewport, game.batch, lifeManager, coinsManager, this, this, waveManager);

        InputHandler inputHandler = new InputHandler(viewport, mapHandler, managerUI, towerManager, this);

        multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(managerUI.getStage());
        multiplexer.addProcessor(inputHandler);

        Gdx.input.setInputProcessor(multiplexer);

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

    public boolean isPaused() {
        return paused;
    }

    @Override
    public void onEvent(GameEvent event) {
        switch (event.type) {
            case BUILD_TOWER:
                Object[] data = (Object[]) event.data;
                TowerSlot slot = (TowerSlot) data[0];
                String type = (String) data[1];

                int cost = getTowerCost(type);

                if (coinsManager.getCurrentBalance() >= cost) {
                    towerManager.buildTower(slot, type, cost, coinsManager);
                    Assets.getSound("sfx/plant.wav").play(SettingsManager.getSfxVolume());
                } else {
                    Assets.getSound("sfx/nofundssound.wav").play(SettingsManager.getSfxVolume());
                }
                break;

            case SELL_TOWER:
                if (event.data instanceof TowerBase) {
                    TowerBase tower = (TowerBase) event.data;
                    coinsManager.add(tower.getSellValue());
                    towerManager.sellTower(tower);
                }
                break;

            case UPGRADE_TOWER:
                if (event.data instanceof TowerBase) {
                    TowerBase tower = (TowerBase) event.data;

                    if (coinsManager.getCurrentBalance() >= tower.getUpgradeCost()) {
                        towerManager.upgradeTower(tower, coinsManager);
                    } else {
                        Assets.getSound("sfx/nofundssound.wav").play(SettingsManager.getSfxVolume());
                    }
                }
                break;
        }
    }

    private int getTowerCost(String type) {
        if (type.equalsIgnoreCase("Tree")) {
            return TowerTree.COST;
        } else if (type.equalsIgnoreCase("Solar")) {
            return TowerSolar.COST;
        } else if (type.equalsIgnoreCase("Wind")) {
            return TowerWind.COST;
        }
        return 100;
    }

    public Main getGame() {
        return game;
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        if (lifeManager.getCurrentLives() <= 0) {
            game.fadeToMusic(null);
            game.setScreen(new GameOverScreen(game));
            this.dispose();
            return;
        }

        if (waveManager.isGameWon()) {
            game.fadeToMusic(null);
            game.setScreen(new WinScreen(game));
            this.dispose();
            return;
        }

        if (!paused) {
            camera.update();
            towerManager.update(delta, enemyManager.getEnemies(), coinsManager);
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
