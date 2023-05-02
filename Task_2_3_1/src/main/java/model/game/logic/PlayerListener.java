package model.game.logic;

import model.units.Snake;
import model.units.snake.Direction;

abstract public class PlayerListener {
    private final Game game;
    private final Snake mySnake;

    public PlayerListener(Game game, Snake botSnake) {
        this.game = game;
        mySnake = botSnake;
    }

    abstract public Direction nextDirection();
}
