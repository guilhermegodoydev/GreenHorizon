package io.github.guilhermegodoydev.greenhorizon;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.guilhermegodoydev.greenhorizon.core.input.InputHandler;
import io.github.guilhermegodoydev.greenhorizon.core.map.MapHandler;
import io.github.guilhermegodoydev.greenhorizon.core.managers.ManagerUI;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private ManagerUI ManagerUI;
    private MapHandler mapHandler;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private FitViewport viewport;

    @Override
    public void create() {
        batch = new SpriteBatch();
        ManagerUI = new ManagerUI();
        mapHandler = new MapHandler("mapa.tmx");
        renderer = new OrthogonalTiledMapRenderer(mapHandler.getTiledMap());
        camera = new OrthographicCamera();
        viewport = new FitViewport(320, 240, camera);

        InputHandler inputHandler = new InputHandler(viewport, mapHandler, ManagerUI);
        Gdx.input.setInputProcessor(inputHandler);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 0, 1);

        renderer.setView(camera);
        renderer.render();

        camera.update();
        viewport.apply();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        ManagerUI.render(batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        ManagerUI.dispose();
        batch.dispose();
        mapHandler.dispose();
        renderer.dispose();
    }
}
