package io.github.guilhermegodoydev.greenhorizon;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private TiledMap mapa;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private FitViewport viewport;

    @Override
    public void create() {
        mapa = new TmxMapLoader().load("mapa.tmx");
        renderer = new OrthogonalTiledMapRenderer(mapa);

        camera = new OrthographicCamera();
        viewport = new FitViewport(320, 240, camera);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 0, 1);

        viewport.apply();
        renderer.setView(camera);
        renderer.render();
    }

    @Override
    public void dispose() {
        mapa.dispose();
        renderer.dispose();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }
}
