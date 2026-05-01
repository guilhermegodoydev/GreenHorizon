package io.github.guilhermegodoydev.greenhorizon.core.managers;

public class WaveManager {
    private final EnemyManager enemyManager;
    private float waveTimer;
    private final float TIME_BETWEEN_WAVES = 20f;
    private int currentWave = 0;
    private boolean waveActive = false;

    // NOVAS VARIÁVEIS PARA O SPAWN ESPAÇADO
    private int inimigosRestantesParaSpawnar = 0;
    private float spawnTimer = 0;
    private final float SPAWN_DELAY = 1.0f; // 1 segundo entre cada inimigo

    public WaveManager(EnemyManager enemyManager) {
        this.enemyManager = enemyManager;
        this.waveTimer = 10f;
    }

    public void update(float delta) {
        if (!waveActive) {
            waveTimer -= delta;
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
                    spawnTimer = SPAWN_DELAY;
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

        waveActive = true;
        currentWave++;

        // Define quantos devem nascer, mas não spawna todos de uma vez
        this.inimigosRestantesParaSpawnar = 3 + (currentWave * 2);
        this.spawnTimer = 0; // Começa o primeiro imediatamente
    }

    public void setWaveActive(boolean active) { this.waveActive = active; }
    public boolean isWaveActive() { return waveActive; }
    public float getWaveTimer() { return waveTimer; }
}
