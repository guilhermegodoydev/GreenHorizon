package io.github.guilhermegodoydev.greenhorizon.core.features.enemy;

import io.github.guilhermegodoydev.greenhorizon.core.utils.Assets;
import io.github.guilhermegodoydev.greenhorizon.core.utils.SettingsManager;

public class WaveManager {
    private final EnemyManager enemyManager;
    private float waveTimer;
    private int currentWave = 0;
    private boolean waveActive = false;

    private final int TOTAL_WAVES = 10;
    private int remainingEnemiesToSpawn = 0;
    private float spawnTimer = 0;

    public WaveManager(EnemyManager enemyManager) {
        this.enemyManager = enemyManager;
        this.waveTimer = 10f;
    }

    private float getDynamicSpawnDelay() {
        return Math.max(0.5f, 1.0f - (currentWave * 0.05f));
    }

    public void update(float delta) {
        if (!waveActive) {
            if (currentWave < TOTAL_WAVES) {
                waveTimer -= delta;
                if (waveTimer <= 0) {
                    startNextWave();
                }
            }
        } else {
            if (remainingEnemiesToSpawn > 0) {
                spawnTimer -= delta;
                if (spawnTimer <= 0) {
                    String enemyType = "GAS";

                    if (currentWave >= 4 && Math.random() <= 0.3f) {
                        enemyType = "BAG";
                    }

                    enemyManager.spawnEnemy(enemyType, currentWave);
                    remainingEnemiesToSpawn--;

                    spawnTimer = getDynamicSpawnDelay();
                }
            }

            if (remainingEnemiesToSpawn == 0 && enemyManager.getEnemies().size == 0) {
                waveActive = false;
                if (currentWave < TOTAL_WAVES) {
                    waveTimer = 20f;
                }
            }
        }
    }

    public void startNextWave() {
        if (waveActive || currentWave >= TOTAL_WAVES) return;
        Assets.getSound("sfx/startwavesound.wav").play(SettingsManager.getSfxVolume());
        waveActive = true;
        currentWave++;

        this.remainingEnemiesToSpawn = 5 + (currentWave * 3);
        this.spawnTimer = getDynamicSpawnDelay();
    }

    public boolean isGameWon() {
        return currentWave == TOTAL_WAVES && !waveActive && enemyManager.getEnemies().size == 0;
    }

    public int getCurrentWave() { return currentWave; }
    public int getTotalWaves() { return TOTAL_WAVES; }

    public boolean isWaveActive() { return waveActive; }
    public float getWaveTimer() { return waveTimer; }
}
