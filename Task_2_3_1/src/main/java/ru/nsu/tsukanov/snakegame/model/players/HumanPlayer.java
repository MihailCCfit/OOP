package ru.nsu.tsukanov.snakegame.model.players;

import ru.nsu.tsukanov.snakegame.model.basic.Point;
import ru.nsu.tsukanov.snakegame.model.game.logic.Game;
import ru.nsu.tsukanov.snakegame.model.units.snake.Direction;

/**
 * Player who can change the state of the passed snake. Accordingly, he did not notice the game on the screen.
 */
public class HumanPlayer extends PlayerListener {
    private Direction direction = Direction.getRandomDirection();

    public HumanPlayer(Game game, Integer snakeId) {
        super(game, snakeId);
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public Direction nextDirection() {
        return direction;
    }

    public Point getCoordinates() {
        return game.getSnakeMap().get(mySnakeId).getHead().getCopy();
    }

    public int getScore() {
        return game.getSnakeMap().get(mySnakeId).getBody().size();
    }
}
