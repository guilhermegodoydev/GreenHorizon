package io.github.guilhermegodoydev.greenhorizon.core.managers;

import io.github.guilhermegodoydev.greenhorizon.core.utils.Assets;

public class WaveManager {
    private final EnemyManager enemyManager;
    private float waveTimer;
    private final float TIME_BETWEEN_WAVES = 20f;
    private int currentWave = 0;
    private boolean waveActive = false;

    private final int TOTAL_WAVES = 10;
    private int inimigosRestantesParaSpawnar = 0;
    private float spawnTimer = 0;
    private final float SPAWN_DELAY = 1.0f;

    public WaveManager(EnemyManager enemyManager) {
        this.enemyManager = enemyManager;
        this.waveTimer = 10f;
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
            if (inimigosRestantesParaSpawnar > 0) {
                spawnTimer -= delta;
                if (spawnTimer <= 0) {
                    // INTELIGÊNCIA DA WAVE
                    String tipoInimigo = "GAS";

                    // A partir da Wave 4, 30% de chance de nascer uma SACOLA
                    if (currentWave >= 4 && Math.random() <= 0.3f) {
                        tipoInimigo = "SACOLA";
                    }

                    // Passamos a currentWave para o inimigo saber quanto de vida bônus ele tem
                    enemyManager.spawnEnemy(tipoInimigo, currentWave);
                    inimigosRestantesParaSpawnar--;
                    spawnTimer = SPAWN_DELAY;
                }
            }

            if (inimigosRestantesParaSpawnar == 0 && enemyManager.getEnemies().size == 0) {
                waveActive = false;
                if (currentWave < TOTAL_WAVES) {
                    waveTimer = TIME_BETWEEN_WAVES;
                }
            }
        }
    }

    public void startNextWave() {
        if (waveActive || currentWave >= TOTAL_WAVES) return;
        Assets.getSound("sfx/startwavesound.wav").play(SettingsManager.getSfxVolume());
        waveActive = true;
        currentWave++;

        // Aumentando exponencialmente o número de inimigos para pressionar o jogador
        this.inimigosRestantesParaSpawnar = 5 + (currentWave * 3);
        this.spawnTimer = 1.0f;
    }

    public boolean isGameWon() {
        return currentWave == TOTAL_WAVES && !waveActive && enemyManager.getEnemies().size == 0;
    }

    public int getCurrentWave() { return currentWave; }
    public int getTotalWaves() { return TOTAL_WAVES; }
    public void setWaveActive(boolean active) { this.waveActive = active; }
    public boolean isWaveActive() { return waveActive; }
    public float getWaveTimer() { return waveTimer; }
}
