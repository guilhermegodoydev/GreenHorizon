package io.github.guilhermegodoydev.greenhorizon;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.guilhermegodoydev.greenhorizon.core.screens.MainMenuScreen;
import io.github.guilhermegodoydev.greenhorizon.core.managers.SettingsManager;
import io.github.guilhermegodoydev.greenhorizon.core.utils.Assets;

public class Main extends Game {
    public SpriteBatch batch;
    public static Cursor defaultCursor;
    public static Cursor clickCursor;

    private Music currentMusic;
    private float targetVolume = 0f;
    private float currentVolume = 0f;
    private final float fadeSpeed = 0.8f;
    private String nextMusicPath = null;
    private String currentMusicPath = null;

    @Override
    public void create() {
        batch = new SpriteBatch();
        configureCursor();
        this.setScreen(new MainMenuScreen(this));
    }

    private void configureCursor() {
        try {
            Pixmap pixNormal = new Pixmap(Gdx.files.internal("cursor.png"));
            defaultCursor = Gdx.graphics.newCursor(pixNormal, 0, 0);

            Pixmap pixClick = new Pixmap(Gdx.files.internal("cursor_hand.png"));
            clickCursor = Gdx.graphics.newCursor(pixClick, 16, 0);

            Gdx.graphics.setCursor(defaultCursor);

            pixNormal.dispose();
            pixClick.dispose();
        } catch (Exception e) {
            Gdx.app.error("Main", "Error loading cursors: " + e.getMessage());
        }
    }

    public void fadeToMusic(String path) {
        if (path == null) {
            nextMusicPath = null;
            currentMusicPath = null;
            targetVolume = 0f;
            return;
        }

        if (path.equals(currentMusicPath) && nextMusicPath == null) {
            targetVolume = SettingsManager.getMusicVolume();
            return;
        }

        if (currentVolume <= 0f) {
            nextMusicPath = path;
            startNextMusic();
            return;
        }

        if (currentMusic == null) {
            nextMusicPath = path;
            currentVolume = 0f;
            targetVolume = SettingsManager.getMusicVolume();
            startNextMusic();
        } else {
            nextMusicPath = path;
            targetVolume = 0f;
        }
    }

    public void syncConfigVolume() {
        if (nextMusicPath == null) {
            targetVolume = SettingsManager.getMusicVolume();
        }
    }

    private void startNextMusic() {
        if (nextMusicPath != null) {
            if (currentMusic != null) {
                currentMusic.stop();
            }

            currentMusicPath = nextMusicPath;
            currentMusic = Assets.getMusic(currentMusicPath);
            currentMusic.setLooping(true);
            currentMusic.setVolume(0f);
            currentMusic.play();

            currentVolume = 0f;
            targetVolume = SettingsManager.getMusicVolume();
            nextMusicPath = null;
        }
    }

    @Override
    public void render() {
        if (currentVolume != targetVolume) {
            float delta = Gdx.graphics.getDeltaTime();

            if (currentVolume < targetVolume) {
                currentVolume += fadeSpeed * delta;
                if (currentVolume > targetVolume) currentVolume = targetVolume;
            } else if (currentVolume > targetVolume) {
                currentVolume -= fadeSpeed * delta;
                if (currentVolume < targetVolume) currentVolume = targetVolume;
            }

            if (currentMusic != null) {
                currentMusic.setVolume(currentVolume);
            }
        }

        if (currentVolume <= 0f) {
            if (nextMusicPath != null) {
                startNextMusic();
            } else if (targetVolume == 0f && currentMusicPath == null) {
                if (currentMusic != null) {
                    currentMusic.stop();
                    currentMusic = null;
                }
            }
        }

        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        Assets.dispose();

        if (defaultCursor != null) defaultCursor.dispose();
        if (clickCursor != null) clickCursor.dispose();
    }
}
