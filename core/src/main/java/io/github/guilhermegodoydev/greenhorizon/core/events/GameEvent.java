package io.github.guilhermegodoydev.greenhorizon.core.events;

public class GameEvent {
    public enum Type { BUILD_TOWER, SELL_TOWER, UPGRADE_TOWER }

    public final Type type;
    public final Object data;

    public GameEvent(Type type, Object data) {
        this.type = type;
        this.data = data;
    }
}
