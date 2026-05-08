package io.github.guilhermegodoydev.greenhorizon.core.managers;

import io.github.guilhermegodoydev.greenhorizon.core.utils.Assets;

public class WaveManager {
    private final EnemyManager enemyManager;
    private float waveTimer;
    private final float TIME_BETWEEN_WAVES = 20f;
    private int currentWave = 0;
    private boolean waveActive = false;

    // NOVA CONSTANTE: O limite do jogo
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
            // Só diminui o cronômetro se ainda não chegamos na última wave
            if (currentWave < TOTAL_WAVES) {
                waveTimer -= delta;

                // A wave começa exatamente no 0
                if (waveTimer <= 0) {
                    startNextWave();
                }
            }
        } else {
            // LÓGICA DE SPAWN GRADUAL
            if (inimigosRestantesParaSpawnar > 0) {
                spawnTimer -= delta;
                if (spawnTimer <= 0) {
                    enemyManager.spawnEnemy("GAS");
                    inimigosRestantesParaSpawnar--;
                    spawnTimer = SPAWN_DELAY;
                }
            }

            // A wave só acaba quando não há mais ninguém para nascer E a lista está vazia
            if (inimigosRestantesParaSpawnar == 0 && enemyManager.getEnemies().size == 0) {
                waveActive = false;

                // Só reseta o timer se o jogo ainda não acabou
                if (currentWave < TOTAL_WAVES) {
                    waveTimer = TIME_BETWEEN_WAVES;
                }
            }
        }
    }

    public void startNextWave() {
        // Trava de segurança para não passar do limite
        if (waveActive || currentWave >= TOTAL_WAVES) return;

        Assets.getSound("sfx/startwavesound.wav").play(SettingsManager.getSfxVolume());

        waveActive = true;
        currentWave++;

        this.inimigosRestantesParaSpawnar = 3 + (currentWave * 2);
        this.spawnTimer = 1.0f;
    }

    // NOVO MÉTODO: Condição de Vitória
    public boolean isGameWon() {
        return currentWave == TOTAL_WAVES && !waveActive && enemyManager.getEnemies().size == 0;
    }

    // --- NOVOS GETTERS PARA A UI LER O CONTADOR ---
    public int getCurrentWave() { return currentWave; }
    public int getTotalWaves() { return TOTAL_WAVES; }

    public void setWaveActive(boolean active) { this.waveActive = active; }
    public boolean isWaveActive() { return waveActive; }
    public float getWaveTimer() { return waveTimer; }
}
