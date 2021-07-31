package com.syntax.lib.types;

public enum ColorType {
    BLUE("§9"),
    LIGHT_BLUE("§b"),
    RED("§4"),
    LIGHT_RED("§c"),
    GREEN("§2"),
    LIGHT_GREEN("§a"),
    YELLOW("§e"),
    ORANGE("§6"),
    PINK("§d"),
    BLACK("§0"),
    WHITE("§r");

    String color;

    private ColorType(String color) {
        this.color = color;
    }

    public String getColor() {
        return this.color;
    }
}
