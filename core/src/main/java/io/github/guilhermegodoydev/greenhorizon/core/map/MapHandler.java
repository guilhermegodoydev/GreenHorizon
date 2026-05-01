package io.github.guilhermegodoydev.greenhorizon.core.map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import io.github.guilhermegodoydev.greenhorizon.core.utils.Assets;

public class MapHandler {
    private TiledMap tiledMap;
    private final Array<TowerSlot> slots;
    private final String OBJECT_LAYER_NAME = "SlotsTorres";
    private final Texture slotTexture;

    public MapHandler(String path) {
        this.tiledMap = new TmxMapLoader().load(path);
        this.slots = new Array<>();

        this.slotTexture = Assets.getTexture("slot_base.png");

        loadSlots();
    }

    private void loadSlots() {
        if (tiledMap.getLayers().get(OBJECT_LAYER_NAME) == null) {
            System.err.println("ERRO: Camada de objetos '" + OBJECT_LAYER_NAME + "' não encontrada no Tiled!");
            return;
        }

        for (MapObject object : tiledMap.getLayers().get(OBJECT_LAYER_NAME).getObjects()) {
            if (object instanceof RectangleMapObject) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                slots.add(new TowerSlot(rect));
            }
        }
    }

    public void renderSlots(SpriteBatch batch) {
        for (TowerSlot slot : slots) {
            if (!slot.isOccupied()) {
                Rectangle bounds = slot.getBounds();
                batch.draw(slotTexture, bounds.x, bounds.y, bounds.width, bounds.height);
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

    public Array<Vector2> getWaypoints() {
        Array<Vector2> points = new Array<>();
        if (tiledMap.getLayers().get("WayPoints") != null) {
            MapObject path = tiledMap.getLayers().get("WayPoints").getObjects().get("rota");
            if (path instanceof PolylineMapObject) {
                float[] vertices = ((PolylineMapObject) path).getPolyline().getTransformedVertices();
                for (int i = 0; i < vertices.length; i += 2) {
                    points.add(new Vector2(vertices[i], vertices[i + 1]));
                }
            }
        }
        return points;
    }

    public void dispose() {
        tiledMap.dispose();
    }
}
