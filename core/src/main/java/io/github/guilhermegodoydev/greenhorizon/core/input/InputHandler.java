package io.github.guilhermegodoydev.greenhorizon.core.input;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.guilhermegodoydev.greenhorizon.core.map.MapHandler;
import io.github.guilhermegodoydev.greenhorizon.core.map.TowerSlot; // Importado
import io.github.guilhermegodoydev.greenhorizon.core.managers.ManagerUI;

public class InputHandler extends InputAdapter {
    private Viewport viewport;
    private MapHandler mapHandler;
    private Vector3 touchPoint;
    private ManagerUI managerUI;

    public InputHandler(Viewport viewport, MapHandler mapHandler, ManagerUI managerUI) {
        this.viewport = viewport;
        this.mapHandler = mapHandler;
        this.touchPoint = new Vector3();
        this.managerUI = managerUI;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touchPoint.set(screenX, screenY, 0);
        viewport.unproject(touchPoint);

        boolean menuEstavaAberto = managerUI.isVisivel();

        TowerSlot slotClicado = mapHandler.getClickedSlot(touchPoint.x, touchPoint.y);

        if (menuEstavaAberto) {
            managerUI.fecharMenu();

            if (slotClicado != null && !slotClicado.isOccupied()) {
                managerUI.abrirMenu(slotClicado);
            }
        } else {
            if (slotClicado != null && !slotClicado.isOccupied()) {
                managerUI.abrirMenu(slotClicado);
            }
        }

        return true;
    }
}
