package io.github.guilhermegodoydev.greenhorizon.core.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.guilhermegodoydev.greenhorizon.core.features.tower.TowerManager;
import io.github.guilhermegodoydev.greenhorizon.core.map.MapHandler;
import io.github.guilhermegodoydev.greenhorizon.core.map.TowerSlot;
import io.github.guilhermegodoydev.greenhorizon.core.ui.ManagerUI;
import io.github.guilhermegodoydev.greenhorizon.core.features.tower.entities.TowerBase;
import io.github.guilhermegodoydev.greenhorizon.core.screens.GameScreen;

public class InputHandler extends InputAdapter {
    private Viewport viewport;
    private MapHandler mapHandler;
    private Vector3 touchPoint;
    private ManagerUI managerUI;
    private TowerManager towerManager;
    private GameScreen gameScreen;

    public InputHandler(Viewport viewport, MapHandler mapHandler, ManagerUI managerUI, TowerManager towerManager, GameScreen gameScreen) {
        this.viewport = viewport;
        this.mapHandler = mapHandler;
        this.touchPoint = new Vector3();
        this.managerUI = managerUI;
        this.towerManager = towerManager;
        this.gameScreen = gameScreen;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.ESCAPE) {
            gameScreen.togglePause();
            return true;
        }
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (gameScreen.isPaused()) {
            return true;
        }

        touchPoint.set(screenX, screenY, 0);
        viewport.unproject(touchPoint);

        boolean menuWasOpen = managerUI.isVisible();

        TowerSlot slot = mapHandler.getClickedSlot(touchPoint.x, touchPoint.y);

        if (slot != null) {
            if (slot.isOccupied()) {
                TowerBase tower = towerManager.getTowerAt(slot.getCenterX(), slot.getCenterY());
                managerUI.openActionMenu(tower);
            } else {
                managerUI.openMenu(slot);
            }
        } else {
            managerUI.closeAllMenus();
        }

        return true;
    }
}
