package io.github.guilhermegodoydev.greenhorizon.core.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class Assets {
    private static final ObjectMap<String, Texture> textures = new ObjectMap<>();

    private static final ObjectMap<String, Sound> sounds = new ObjectMap<>();
    private static final ObjectMap<String, Music> musics = new ObjectMap<>();

    /**
     * Retorna a textura do caminho especificado.
     * Se ela não estiver na memória, carrega e aplica o filtro Nearest.
     */
    public static Texture getTexture(String path) {
        if (!textures.containsKey(path)) {
            Texture tex = new Texture(path);
            tex.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
            textures.put(path, tex);
        }
        return textures.get(path);
    }

    public static Sound getSound(String path) {
        if (!sounds.containsKey(path)) {
            // Como é Resource Root, o Gdx.files.internal acha pelo nome direto
            Sound sound = Gdx.audio.newSound(Gdx.files.internal(path));
            sounds.put(path, sound);
        }
        return sounds.get(path);
    }

    public static Music getMusic(String path) {
        if (!musics.containsKey(path)) {
            Music music = Gdx.audio.newMusic(Gdx.files.internal(path));
            musics.put(path, music);
        }
        return musics.get(path);
    }

    /**
     * Limpa todas as texturas da memória.
     * Deve ser chamado no dispose() da classe Main.
     */
    public static void dispose() {
        for (Texture tex : textures.values()) {
            tex.dispose();
        }
        textures.clear();

        for (Sound sound : sounds.values()) {
            sound.dispose();
        }
        sounds.clear();

        for (Music music : musics.values()) {
            music.dispose();
        }
        musics.clear();
    }
}
