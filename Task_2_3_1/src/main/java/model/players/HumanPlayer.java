package model.players;

import model.game.logic.Game;
import model.game.logic.PlayerListener;
import model.units.snake.Direction;

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
}
