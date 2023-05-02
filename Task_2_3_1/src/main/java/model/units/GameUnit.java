package model.units;

import model.basic.Point;

/**
 * Это база.
 */
public abstract class GameUnit extends Point {
    public GameUnit(int x, int y) {
        super(x, y);
    }

    public GameUnit(GameUnit unit) {
        super(unit.getX(), unit.getY());
    }

    abstract public GameUnit getCopy();


}
