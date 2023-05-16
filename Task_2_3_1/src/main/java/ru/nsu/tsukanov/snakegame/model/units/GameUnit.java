package ru.nsu.tsukanov.snakegame.model.units;

import ru.nsu.tsukanov.snakegame.model.basic.Point;

/**
 * Base of game logic after point.
 */
public abstract sealed class GameUnit extends Point permits Empty, Food, SnakeBody, Wall {
    public GameUnit(int x, int y) {
        super(x, y);
    }

    public GameUnit(GameUnit unit) {
        super(unit.getX(), unit.getY());
    }

    /**
     * Create new instance of this.
     *
     * @return new instance of gameUnit
     */
    abstract public GameUnit getCopy();

    public GameUnit getCopy(int x, int y) {
        GameUnit gameUnit = getCopy();
        gameUnit.setX(x);
        gameUnit.setY(y);
        return gameUnit;
    }


}
