package model.units.util;

import model.units.Empty;
import model.units.Food;
import model.units.GameUnit;
import model.units.SnakeBody;

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