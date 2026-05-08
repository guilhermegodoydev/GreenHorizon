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

    public WaveManager(EnemyManager enemyManager) {
        this.enemyManager = enemyManager;
        this.waveTimer = 10f;
    }

    // NOVA LÓGICA DE PRESSÃO: O tempo de spawn diminui a cada wave
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
            if (inimigosRestantesParaSpawnar > 0) {
                spawnTimer -= delta;
                if (spawnTimer <= 0) {
                    String tipoInimigo = "GAS";

                    if (currentWave >= 4 && Math.random() <= 0.3f) {
                        tipoInimigo = "SACOLA";
                    }

                    enemyManager.spawnEnemy(tipoInimigo, currentWave);
                    inimigosRestantesParaSpawnar--;

                    // Aplica o delay dinâmico e mais rápido!
                    spawnTimer = getDynamicSpawnDelay();
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

        this.inimigosRestantesParaSpawnar = 5 + (currentWave * 3);

        // Aplica o delay dinâmico no primeiro inimigo
        this.spawnTimer = getDynamicSpawnDelay();
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
