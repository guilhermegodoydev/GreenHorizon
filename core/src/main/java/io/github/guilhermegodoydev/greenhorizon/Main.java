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

        // CORREÇÃO: Atalho para o silêncio. Se já estamos no volume 0, troca a música imediatamente!
        if (volumeAtual <= 0f) {
            nextMusicPath = path;
            startNextMusic();
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

            // Garante que a nova música nasça no 0, pronta para subir se o volumeAlvo for maior que 0
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
        }

        // CORREÇÃO: Gatilho de troca e destruição tirado de dentro do if (volumeAtual != volumeAlvo).
        // Agora ele avalia a necessidade de trocar ou parar a música em TODOS os frames que o volume estiver zerado.
        if (volumeAtual <= 0f) {
            if (nextMusicPath != null) {
                startNextMusic();
            } else if (volumeAlvo == 0f && currentMusicPath == null) {
                // Só destrói a música se o caminho foi intencionalmente apagado (Game Over)
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
    }
}
