package io.github.guilhermegodoydev.greenhorizon.core.input;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.guilhermegodoydev.greenhorizon.core.managers.TowerManager;
import io.github.guilhermegodoydev.greenhorizon.core.map.MapHandler;
import io.github.guilhermegodoydev.greenhorizon.core.map.TowerSlot;
import io.github.guilhermegodoydev.greenhorizon.core.managers.ManagerUI;
import io.github.guilhermegodoydev.greenhorizon.core.entities.towers.TowerBase;

public class InputHandler extends InputAdapter {
    private Viewport viewport;
    private MapHandler mapHandler;
    private Vector3 touchPoint;
    private ManagerUI managerUI;
    private TowerManager towerManager;

    public InputHandler(Viewport viewport, MapHandler mapHandler, ManagerUI managerUI,TowerManager towerManager) {
        this.viewport = viewport;
        this.mapHandler = mapHandler;
        this.touchPoint = new Vector3();
        this.managerUI = managerUI;
        this.towerManager = towerManager;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
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
