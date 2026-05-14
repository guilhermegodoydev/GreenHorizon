package io.github.guilhermegodoydev.greenhorizon.core.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.MathUtils;

public class SettingsManager {
    private static final String PREFS_NAME = "GreenHorizon_Settings";
    private static final String MUSIC_KEY = "music_volume";
    private static final String SFX_KEY = "sfx_volume";

    private static Preferences getPrefs() {
        return Gdx.app.getPreferences(PREFS_NAME);
    }

    public static float getMusicVolume() {
        return getPrefs().getFloat(MUSIC_KEY, 0.5f);
    }

    public static void setMusicVolume(float volume) {
        float cleanedVolume = Math.round(volume * 10f) / 10f;
        getPrefs().putFloat(MUSIC_KEY, MathUtils.clamp(cleanedVolume, 0f, 1f));
        getPrefs().flush();
    }

    public static float getSfxVolume() {
        return getPrefs().getFloat(SFX_KEY, 0.8f);
    }

    public static void setSfxVolume(float volume) {
        float cleanedVolume = Math.round(volume * 10f) / 10f;
        getPrefs().putFloat(SFX_KEY, MathUtils.clamp(cleanedVolume, 0f, 1f));
        getPrefs().flush();
    }
}
