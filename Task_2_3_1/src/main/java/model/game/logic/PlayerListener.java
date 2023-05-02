package model.game.logic;

import model.units.Snake;
import model.units.snake.Direction;

abstract public class PlayerListener {
    protected final Game game;
    protected final Integer mySnakeId;

    public PlayerListener(Game game, Integer snakeId) {
        this.game = game;
        mySnakeId = snakeId;
    }

    abstract public Direction nextDirection();
}
