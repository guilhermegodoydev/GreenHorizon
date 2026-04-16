package io.github.guilhermegodoydev.greenhorizon.core.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.guilhermegodoydev.greenhorizon.Main;

public abstract class BaseScreen implements Screen {
    protected final Main game;
    protected OrthographicCamera camera;
    protected FitViewport viewport;

    public BaseScreen(Main game) {
        this.game = game;
        this.camera = new OrthographicCamera();
        this.viewport = new FitViewport(320, 240, camera);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override public void show() {}
    @Override public void hide() {}
    @Override public void pause() {}
    @Override public void resume() {}

    @Override public abstract void render(float delta);
    @Override public abstract void dispose();
}
