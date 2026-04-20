package io.github.guilhermegodoydev.greenhorizon.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.guilhermegodoydev.greenhorizon.Main;
import io.github.guilhermegodoydev.greenhorizon.core.input.InputHandler;
import io.github.guilhermegodoydev.greenhorizon.itens.LifeManager;
import io.github.guilhermegodoydev.greenhorizon.core.managers.ManagerUI;
import io.github.guilhermegodoydev.greenhorizon.core.managers.TowerManager;
import io.github.guilhermegodoydev.greenhorizon.core.map.MapHandler;

public class GameScreen extends BaseScreen {
    private final MapHandler mapHandler;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private final ManagerUI managerUI;
    private final TowerManager towerManager;
    private final LifeManager lifeManager;

    public GameScreen(Main game) {
        super(game);

        lifeManager = new LifeManager(10);
        mapHandler = new MapHandler("mapa.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(mapHandler.getTiledMap());

        towerManager = new TowerManager();
        managerUI = new ManagerUI(viewport, game.batch, lifeManager, towerManager);

        InputHandler inputHandler = new InputHandler(viewport, mapHandler, managerUI, towerManager);

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(managerUI.getStage());
        multiplexer.addProcessor(inputHandler);

        Gdx.input.setInputProcessor(multiplexer);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        camera.update();
        towerManager.update(delta);

        mapRenderer.setView(camera);
        mapRenderer.render();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();

        mapHandler.renderSlots(game.batch);

        towerManager.render(game.batch);

        game.batch.end();

        managerUI.render(delta);

        // Teste de Dano
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.SPACE)) {
            lifeManager.perderVida(1);
        }
    }

    @Override
    public void dispose() {
        mapHandler.dispose();
        mapRenderer.dispose();
        managerUI.dispose();
    }
}
