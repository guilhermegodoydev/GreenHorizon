package io.github.guilhermegodoydev.greenhorizon.core.events;

public record GameEvent(Type type, Object data) {
    public enum Type {BUILD_TOWER, SELL_TOWER, UPGRADE_TOWER}

}
