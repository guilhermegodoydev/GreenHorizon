package io.github.guilhermegodoydev.greenhorizon;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.guilhermegodoydev.greenhorizon.core.screens.MainMenuScreen;
import io.github.guilhermegodoydev.greenhorizon.core.managers.SettingsManager;
import io.github.guilhermegodoydev.greenhorizon.core.utils.Assets;

public class Main extends Game {
    public SpriteBatch batch;

    // --- GERENCIAMENTO DE ÁUDIO ---
    private Music currentMusic;
    private float volumeAlvo = 0f;
    private float volumeAtual = 0f;
    private final float fadeSpeed = 0.8f;
    private String nextMusicPath = null;

    // Variável adicionada para evitar reset da mesma música
    private String currentMusicPath = null;

    @Override
    public void create() {
        batch = new SpriteBatch();
        this.setScreen(new MainMenuScreen(this));
    }

    public void fadeToMusic(String path) {
        // 1. Estado de Silêncio (Fade-out total)
        if (path == null) {
            nextMusicPath = null;
            currentMusicPath = null;
            volumeAlvo = 0f;
            return;
        }

        // 2. Prevenção de Reset: Se a música pedida já é a atual, apenas garante o volume e ignora
        if (path.equals(currentMusicPath) && nextMusicPath == null) {
            volumeAlvo = SettingsManager.getMusicVolume();
            return;
        }

        // 3. Transição ou Inicialização
        if (currentMusic == null) {
            nextMusicPath = path;
            volumeAtual = 0f;
            volumeAlvo = SettingsManager.getMusicVolume();
            startNextMusic();
        } else {
            nextMusicPath = path;
            volumeAlvo = 0f;
        }
    }

    public void syncConfigVolume() {
        if (nextMusicPath == null) {
            volumeAlvo = SettingsManager.getMusicVolume();
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

            volumeAtual = 0f;
            volumeAlvo = SettingsManager.getMusicVolume();
            nextMusicPath = null;
        }
    }

    @Override
    public void render() {
        // Interpolação de Fade
        if (volumeAtual != volumeAlvo) {
            float delta = Gdx.graphics.getDeltaTime();

            if (volumeAtual < volumeAlvo) {
                volumeAtual += fadeSpeed * delta;
                if (volumeAtual > volumeAlvo) volumeAtual = volumeAlvo;
            } else if (volumeAtual > volumeAlvo) {
                volumeAtual -= fadeSpeed * delta;
                if (volumeAtual < volumeAlvo) volumeAtual = volumeAlvo;
            }

            if (currentMusic != null) {
                currentMusic.setVolume(volumeAtual);
            }

            // CORREÇÃO AQUI: Só processa a parada se o objetivo for chegar no zero (volumeAlvo == 0)
            if (volumeAtual <= 0f && volumeAlvo == 0f) {
                if (nextMusicPath != null) {
                    // Se tem uma próxima música na fila, inicia ela
                    startNextMusic();
                } else if (currentMusic != null && currentMusic.isPlaying()) {
                    // Se não tem próxima música, apenas para o que está tocando (Caso do Game Over)
                    currentMusic.stop();
                    currentMusic = null;
                    currentMusicPath = null;
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
    }
}
