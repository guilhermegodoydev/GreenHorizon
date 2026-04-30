package io.github.guilhermegodoydev.greenhorizon.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.guilhermegodoydev.greenhorizon.Main;
import io.github.guilhermegodoydev.greenhorizon.core.entities.towers.TowerBase;
import io.github.guilhermegodoydev.greenhorizon.core.events.GameEvent;
import io.github.guilhermegodoydev.greenhorizon.core.events.GameEventListener;
import io.github.guilhermegodoydev.greenhorizon.core.input.InputHandler;
import io.github.guilhermegodoydev.greenhorizon.core.managers.ManagerUI;
import io.github.guilhermegodoydev.greenhorizon.core.managers.SettingsManager;
import io.github.guilhermegodoydev.greenhorizon.core.managers.TowerManager;
import io.github.guilhermegodoydev.greenhorizon.core.map.MapHandler;
import io.github.guilhermegodoydev.greenhorizon.core.map.TowerSlot;
import io.github.guilhermegodoydev.greenhorizon.core.itens.LifeManager;
import io.github.guilhermegodoydev.greenhorizon.core.itens.CoinsManager;
import com.badlogic.gdx.audio.Music;
import io.github.guilhermegodoydev.greenhorizon.core.utils.Assets;

public class GameScreen extends BaseScreen implements GameEventListener {
    private final MapHandler mapHandler;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private final ManagerUI managerUI;
    private final TowerManager towerManager;
    private final LifeManager lifeManager;
    private final CoinsManager coinsManager;

    private boolean paused = false;
    private Music bgm;

    // Multiplexer para gerenciar os cliques do mouse
    private InputMultiplexer multiplexer;

    public GameScreen(Main game) {
        super(game);

        lifeManager = new LifeManager(10);
        coinsManager = new CoinsManager(500);
        mapHandler = new MapHandler("mapa.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(mapHandler.getTiledMap());
        towerManager = new TowerManager();

        managerUI = new ManagerUI(viewport, game.batch, lifeManager, coinsManager, this, this);

        InputHandler inputHandler = new InputHandler(viewport, mapHandler, managerUI, towerManager);

        multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(managerUI.getStage());
        multiplexer.addProcessor(inputHandler);

        bgm = Assets.getMusic("bgm.mp3");
        bgm.setLooping(true);
        bgm.setVolume(SettingsManager.getMusicVolume());
        bgm.play();
    }

    public void togglePause() {
        paused = !paused;
        if (managerUI != null) {
            managerUI.setPauseVisible(paused);
        }
    }

    public Main getGame() {
        return this.game;
    }

    @Override
    public void show() {
        super.show();

        // Garante que a tela de jogo retome o controle do mouse
        Gdx.input.setInputProcessor(multiplexer);

        if (bgm != null) {
            bgm.setVolume(SettingsManager.getMusicVolume());
        }
    }

    @Override
    public void onEvent(GameEvent event) {
        if (paused) return;

        switch (event.type) {
            case BUILD_TOWER:
                Object[] data = (Object[]) event.data;
                TowerSlot slot = (TowerSlot) data[0];
                String tipo = (String) data[1];

                towerManager.buildTower(slot, tipo);
                Assets.getSound("plant.wav").play(SettingsManager.getSfxVolume());
                break;
            case SELL_TOWER:
                break;
            case UPGRADE_TOWER:
                break;
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            togglePause();
        }

        if (!paused) {
            towerManager.update(delta);

            // --- SEUS TESTES VOLTARAM AQUI, INTOCADOS ---
            if (Gdx.input.isKeyJustPressed(Input.Keys.C)) {
                coinsManager.acrescentar(100);
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.V)) {
                try {
                    coinsManager.remover(50);
                } catch (Exception e) {
                    System.out.println("Erro: " + e.getMessage());
                }
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                lifeManager.perderVida(1);
            }
            // ---------------------------------------------
        }

        camera.update();
        mapRenderer.setView(camera);
        mapRenderer.render();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        mapHandler.renderSlots(game.batch);
        towerManager.render(game.batch);
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
