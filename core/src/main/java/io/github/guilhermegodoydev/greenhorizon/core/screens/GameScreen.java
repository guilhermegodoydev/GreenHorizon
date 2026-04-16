package io.github.guilhermegodoydev.greenhorizon.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.guilhermegodoydev.greenhorizon.Main;
import io.github.guilhermegodoydev.greenhorizon.core.input.InputHandler;
import io.github.guilhermegodoydev.greenhorizon.core.managers.LifeManager;
import io.github.guilhermegodoydev.greenhorizon.core.managers.ManagerUI;
import io.github.guilhermegodoydev.greenhorizon.core.managers.TowerManager;
import io.github.guilhermegodoydev.greenhorizon.core.map.MapHandler;

public class GameScreen extends BaseScreen {
    private MapHandler mapHandler;
    private OrthogonalTiledMapRenderer mapRenderer;
    private ManagerUI managerUI;
    private TowerManager towerManager;
    private LifeManager lifeManager;

    public GameScreen(Main game) {
        super(game);

        lifeManager = new LifeManager(10);
        mapHandler = new MapHandler("mapa.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(mapHandler.getTiledMap());
        managerUI = new ManagerUI(viewport, game.batch, lifeManager);
        towerManager = new TowerManager();

        InputHandler inputHandler = new InputHandler(viewport, mapHandler, managerUI);
        Gdx.input.setInputProcessor(inputHandler);
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
        towerManager.render(game.batch);
        game.batch.end();

        managerUI.render(delta);

        // --- TESTE DE DANO TEMPORÁRIO ---
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.SPACE)) {
            lifeManager.perderVida(1);
            System.out.println("Vidas restantes: " + lifeManager.getVidasAtuais());
        }

    }

    @Override
    public void dispose() {
        mapHandler.dispose();
        mapRenderer.dispose();
        managerUI.dispose();
    }
}
