package io.github.guilhermegodoydev.greenhorizon.core.map;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class MapHandler {
    private TiledMap tiledMap;
    private final Array<TowerSlot> slots;
    private final String OBJECT_LAYER_NAME = "SlotsTorres";

    public MapHandler(String path) {
        this.tiledMap = new TmxMapLoader().load(path);
        this.slots = new Array<>();
        loadSlots();
    }

    private void loadSlots() {
        for (MapObject object : tiledMap.getLayers().get(OBJECT_LAYER_NAME).getObjects()) {
            if (object instanceof RectangleMapObject) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                slots.add(new TowerSlot(rect));
            }
        }
    }

    public TowerSlot getClickedSlot(float worldX, float worldY) {
        for (TowerSlot slot : slots) {
            if (slot.isHit(worldX, worldY)) {
                return slot;
            }
        }
        return null;
    }

    public TiledMap getTiledMap() {
        return tiledMap;
    }

    public void dispose() {
        tiledMap.dispose();
    }
}
