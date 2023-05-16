package ru.nsu.tsukanov.snakegame.model.players;

import ru.nsu.tsukanov.snakegame.model.basic.Point;
import ru.nsu.tsukanov.snakegame.model.game.logic.Game;
import ru.nsu.tsukanov.snakegame.model.units.snake.Direction;

/**
 * Игрок, который может менять состояние переданной змеи. Соответственно он не влияет на игру напрямую.
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
