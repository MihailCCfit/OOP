package ru.nsu.tsukanov.snakegame.model.units.util;

public enum UnitType {
    Empty(0),
    SnakeBody(1),
    Food(2),
    Wall(3);
    final int intType;

    UnitType(int intType) {
        this.intType = intType;
    }

    public int getIntType() {
        return intType;
    }

}