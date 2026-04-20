package io.github.guilhermegodoydev.greenhorizon.core.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.utils.ObjectMap;

public class Assets {
    private static final ObjectMap<String, Texture> textures = new ObjectMap<>();

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

    /**
     * Limpa todas as texturas da memória.
     * Deve ser chamado no dispose() da classe Main.
     */
    public static void dispose() {
        for (Texture tex : textures.values()) {
            tex.dispose();
        }
        textures.clear();
    }
}
