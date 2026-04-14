package io.github.guilhermegodoydev.greenhorizon.core.map;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;

public class MapHandler {
    private TiledMap tiledMap;
    private final String OBJECT_LAYER_NAME = "SlotsTorres";

    public MapHandler(String path) {
        this.tiledMap = new TmxMapLoader().load(path);
    }

    public MapObject checkClickInSlot(float worldX, float worldY) {
        for (MapObject object : tiledMap.getLayers().get(OBJECT_LAYER_NAME).getObjects()) {
            if (object instanceof RectangleMapObject) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();

                if (rect.contains(worldX, worldY)) {
                    return object;
                }
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
