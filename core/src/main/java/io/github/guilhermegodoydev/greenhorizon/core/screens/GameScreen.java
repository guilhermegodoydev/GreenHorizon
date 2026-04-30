package io.github.guilhermegodoydev.greenhorizon.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.guilhermegodoydev.greenhorizon.Main;
import io.github.guilhermegodoydev.greenhorizon.core.entities.towers.TowerBase;
import io.github.guilhermegodoydev.greenhorizon.core.entities.towers.TowerTree;
import io.github.guilhermegodoydev.greenhorizon.core.events.GameEvent;
import io.github.guilhermegodoydev.greenhorizon.core.events.GameEventListener;
import io.github.guilhermegodoydev.greenhorizon.core.input.InputHandler;
import io.github.guilhermegodoydev.greenhorizon.core.managers.ManagerUI;
import io.github.guilhermegodoydev.greenhorizon.core.managers.TowerManager;
import io.github.guilhermegodoydev.greenhorizon.core.map.MapHandler;
import io.github.guilhermegodoydev.greenhorizon.core.map.TowerSlot;
import io.github.guilhermegodoydev.greenhorizon.core.itens.LifeManager;
import io.github.guilhermegodoydev.greenhorizon.core.itens.CoinsManager; // ← ADICIONE ESTE IMPORT
import com.badlogic.gdx.audio.Music;
import io.github.guilhermegodoydev.greenhorizon.core.utils.Assets;

public class GameScreen extends BaseScreen implements GameEventListener {
    private final MapHandler mapHandler;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private final ManagerUI managerUI;
    private final TowerManager towerManager;
    private final LifeManager lifeManager;
    private final CoinsManager coinsManager; // ← ADICIONE ESTE ATRIBUTO

    public GameScreen(Main game) {
        super(game);

        lifeManager = new LifeManager(10);
        coinsManager = new CoinsManager(200); // ← ADICIONE ESTA LINHA (500 moedas iniciais)
        mapHandler = new MapHandler("mapa.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(mapHandler.getTiledMap());
        towerManager = new TowerManager();

        managerUI = new ManagerUI(viewport, game.batch, lifeManager, coinsManager, this); // ← MODIFIQUE ESTA LINHA

        InputHandler inputHandler = new InputHandler(viewport, mapHandler, managerUI, towerManager);

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(managerUI.getStage());
        multiplexer.addProcessor(inputHandler);

        Gdx.input.setInputProcessor(multiplexer);

        Music bgm = Assets.getMusic("bgm.mp3");
        bgm.setLooping(true);  // Para a música não parar
        bgm.setVolume(0.5f);   // Volume médio
        bgm.play();            // SOLTA O SOM!
    }

    @Override
    public void onEvent(GameEvent event) {
        switch (event.type) {
            case BUILD_TOWER:
                Object[] data = (Object[]) event.data;
                TowerSlot slot = (TowerSlot) data[0];
                String tipo = (String) data[1];

                int custo = 0;

                if (tipo.equalsIgnoreCase("Arvore")) {
                    custo = TowerTree.CUSTO;
                }

                towerManager.buildTower(slot, tipo, custo, coinsManager);
                // ==========================================
                // PASSO 3: TOCAR SOM AO CONSTRUIR
                // ==========================================
                Assets.getSound("plant.wav").play();
                break;

            case SELL_TOWER:
                TowerBase torreParaVender = (TowerBase) event.data;
                System.out.println("Lógica de venda iniciada para: " + torreParaVender);
                // Futuramente: towerManager.remove(torreParaVender);
                break;

            case UPGRADE_TOWER:
                System.out.println("Lógica de upgrade iniciada...");
                break;
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        camera.update();
        towerManager.update(delta);

        mapRenderer.setView(camera);
        mapRenderer.render();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        mapHandler.renderSlots(game.batch);
        towerManager.render(game.batch);
        game.batch.end();

        managerUI.render(delta);

        //Teste: perder via precione espaço
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.SPACE)) {
            lifeManager.perderVida(1);
        }
    }

    @Override
    public void dispose() {
        mapHandler.dispose();
        mapRenderer.dispose();
        managerUI.dispose();
    }
}
