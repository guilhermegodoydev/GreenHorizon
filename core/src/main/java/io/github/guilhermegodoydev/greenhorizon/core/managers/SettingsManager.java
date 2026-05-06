package io.github.guilhermegodoydev.greenhorizon.core.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.MathUtils; // Importante para o clamp

public class SettingsManager {
    private static final String PREFS_NAME = "GreenHorizon_Settings";
    private static final String MUSIC_KEY = "music_volume";
    private static final String SFX_KEY = "sfx_volume";

    private static Preferences getPrefs() {
        return Gdx.app.getPreferences(PREFS_NAME);
    }

    // --- MÚSICA ---
    public static float getMusicVolume() {
        return getPrefs().getFloat(MUSIC_KEY, 0.5f); // 0.5f é o padrão
    }

    public static void setMusicVolume(float volume) {
        // Arredonda para 1 casa decimal (ex: 0.19999 vira 0.2) e prende entre 0 e 1
        float volumeLimpo = Math.round(volume * 10f) / 10f;
        getPrefs().putFloat(MUSIC_KEY, MathUtils.clamp(volumeLimpo, 0f, 1f));
        getPrefs().flush(); // Salva no disco
    }

    // --- EFEITOS SONOROS (SFX) ---
    public static float getSfxVolume() {
        return getPrefs().getFloat(SFX_KEY, 0.8f); // 0.8f é o padrão
    }

    public static void setSfxVolume(float volume) {
        // Arredonda para 1 casa decimal e prende entre 0 e 1
        float volumeLimpo = Math.round(volume * 10f) / 10f;
        getPrefs().putFloat(SFX_KEY, MathUtils.clamp(volumeLimpo, 0f, 1f));
        getPrefs().flush();
    }
}
