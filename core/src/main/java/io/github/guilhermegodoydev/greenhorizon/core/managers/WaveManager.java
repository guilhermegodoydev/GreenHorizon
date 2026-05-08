package io.github.guilhermegodoydev.greenhorizon.core.managers;

import io.github.guilhermegodoydev.greenhorizon.core.utils.Assets;

public class WaveManager {
    private final EnemyManager enemyManager;
    private float waveTimer;
    private final float TIME_BETWEEN_WAVES = 20f;
    private int currentWave = 0;
    private boolean waveActive = false;

    // NOVAS VARIÁVEIS PARA O SPAWN ESPAÇADO
    private int inimigosRestantesParaSpawnar = 0;
    private float spawnTimer = 0;
    private final float SPAWN_DELAY = 1.0f; // 1 segundo entre cada inimigo (após o primeiro)

    public WaveManager(EnemyManager enemyManager) {
        this.enemyManager = enemyManager;
        this.waveTimer = 10f;
    }

    public void update(float delta) {
        if (!waveActive) {
            waveTimer -= delta;

            // A wave começa exatamente no 0
            if (waveTimer <= 0) {
                startNextWave();
            }
        } else {
            // LÓGICA DE SPAWN GRADUAL
            if (inimigosRestantesParaSpawnar > 0) {
                spawnTimer -= delta;
                if (spawnTimer <= 0) {
                    enemyManager.spawnEnemy("GAS");
                    inimigosRestantesParaSpawnar--;
                    spawnTimer = SPAWN_DELAY; // Os próximos inimigos respeitam o delay padrão de 1s
                }
            }

            // A wave só acaba quando não há mais ninguém para nascer E a lista está vazia
            if (inimigosRestantesParaSpawnar == 0 && enemyManager.getEnemies().size == 0) {
                waveActive = false;
                waveTimer = TIME_BETWEEN_WAVES;
            }
        }
    }

    public void startNextWave() {
        if (waveActive) return;

        // Feedback imediato: a wave começou!
        Assets.getSound("sfx/startwavesound.wav").play(SettingsManager.getSfxVolume());

        waveActive = true;
        currentWave++;

        this.inimigosRestantesParaSpawnar = 3 + (currentWave * 2);

        // A MÁGICA: O primeiro inimigo vai esperar 0.5 segundos para nascer após o som tocar
        this.spawnTimer = 1.0f;
    }

    public void setWaveActive(boolean active) { this.waveActive = active; }
    public boolean isWaveActive() { return waveActive; }
    public float getWaveTimer() { return waveTimer; }
}
