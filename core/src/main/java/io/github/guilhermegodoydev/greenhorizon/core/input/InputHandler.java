package io.github.guilhermegodoydev.greenhorizon.core.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.guilhermegodoydev.greenhorizon.core.managers.TowerManager;
import io.github.guilhermegodoydev.greenhorizon.core.map.MapHandler;
import io.github.guilhermegodoydev.greenhorizon.core.map.TowerSlot;
import io.github.guilhermegodoydev.greenhorizon.core.managers.ManagerUI;
import io.github.guilhermegodoydev.greenhorizon.core.entities.towers.TowerBase;
import io.github.guilhermegodoydev.greenhorizon.core.screens.GameScreen;

public class InputHandler extends InputAdapter {
    private Viewport viewport;
    private MapHandler mapHandler;
    private Vector3 touchPoint;
    private ManagerUI managerUI;
    private TowerManager towerManager;
    private GameScreen gameScreen; // Referência da tela do jogo

    public InputHandler(Viewport viewport, MapHandler mapHandler, ManagerUI managerUI, TowerManager towerManager, GameScreen gameScreen) {
        this.viewport = viewport;
        this.mapHandler = mapHandler;
        this.touchPoint = new Vector3();
        this.managerUI = managerUI;
        this.towerManager = towerManager;
        this.gameScreen = gameScreen; // Atribui a referência
    }

    @Override
    public boolean keyDown(int keycode) {
        // Verifica se a tecla pressionada foi o ESC
        if (keycode == Input.Keys.ESCAPE) {
            gameScreen.togglePause();
            return true; // Indica que o evento foi tratado
        }
        return false; // Passa o evento adiante caso não seja o ESC
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // A Barreira: Se o jogo estiver pausado, consome o clique imediatamente e não interage com o mapa
        if (gameScreen.isPaused()) {
            return true;
        }

        touchPoint.set(screenX, screenY, 0);
        viewport.unproject(touchPoint);

        boolean menuEstavaAberto = managerUI.isVisivel();

        TowerSlot slot = mapHandler.getClickedSlot(touchPoint.x, touchPoint.y);

        if (slot != null) {
            if (slot.isOccupied()) {
                // Busca a torre naquela posição e abre o menu de ação
                TowerBase torre = towerManager.getTowerAt(slot.getCenterX(), slot.getCenterY());
                managerUI.abrirMenuAcao(torre);
            } else {
                managerUI.abrirMenu(slot);
            }
        } else {
            managerUI.fecharTodosMenus();
        }

        return true;
    }
}
