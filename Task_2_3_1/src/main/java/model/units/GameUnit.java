package model.units;

import model.basic.Point2;

public abstract sealed class GameUnit extends Point2 permits Food, SnakeBody, Wall, Empty {
    public GameUnit(int x, int y) {
        super(x, y);
    }

    public GameUnit(GameUnit unit) {
        super(unit.getX(), unit.getY());
    }


}
