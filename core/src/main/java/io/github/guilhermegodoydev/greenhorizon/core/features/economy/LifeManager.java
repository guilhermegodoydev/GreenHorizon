package io.github.guilhermegodoydev.greenhorizon.core.features.economy;

import io.github.guilhermegodoydev.greenhorizon.core.utils.SettingsManager;
import io.github.guilhermegodoydev.greenhorizon.core.utils.Assets;

public class LifeManager {
    private int currentLives;

    public LifeManager(int initialLives) {
        this.currentLives = initialLives;
    }

    public void loseLife(int amount) {
        currentLives -= amount;
        Assets.getSound("sfx/hit.wav").play(SettingsManager.getSfxVolume());
        System.out.println("An enemy passed! Remaining lives: " + currentLives);

        if (currentLives <= 0) {
            currentLives = 0;
            Assets.getSound("sfx/gameOver.wav").play(SettingsManager.getSfxVolume());
            triggerGameOver();
        }
    }

    public void triggerGameOver() {
        System.out.println("Game Over! The base was destroyed");
    }

    public int getCurrentLives() {
        return currentLives;
    }
}
