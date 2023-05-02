package model.players;

import model.game.logic.Game;
import model.game.logic.GameLogic;
import model.game.logic.PlayerListener;
import model.units.GameUnit;
import model.units.Snake;
import model.units.SnakeBody;
import model.units.Wall;
import model.units.snake.Direction;

import java.util.Map;

public class CommonBotPlayer extends PlayerListener {
    private Snake snake;

    public CommonBotPlayer(Game game, Integer snakeId) {
        super(game, snakeId);
        snake = game.getSnakeMap().get(snakeId);
    }

    @Override
    public Direction nextDirection() {
        SnakeBody snakeHead = snake.getHead();
        GameUnit nextUnit = getNextUnit(nextPoint(snakeHead));
        Direction nextDirection = snakeHead.getDirection();
        if (isDangerous(nextUnit)) {
            nextDirection = snakeHead.getDirection().changeDirection(true);
            if (isDangerous(getNextUnit(nextPoint(nextDirection, snakeHead.getX(), snakeHead.getY())))) {
                nextDirection = snakeHead.getDirection().changeDirection(false);
            }
        }
        return nextDirection;
    }

    private Map.Entry<Integer, Integer> nextPoint(Direction direction, int x, int y) {
        return GameLogic.nextStep(direction, x, y);
    }

    private Map.Entry<Integer, Integer> nextPoint(SnakeBody head) {
        return nextPoint(head.getDirection(), head.getX(), head.getY());
    }

    private boolean isDangerous(GameUnit nextUnit) {
        return nextUnit == null || nextUnit instanceof Wall || nextUnit instanceof SnakeBody;
    }

    private GameUnit getNextUnit(Map.Entry<Integer, Integer> pair) {
        int nextX = pair.getKey();
        int nextY = pair.getValue();
        return game.getUnitAt(nextX, nextY);
    }
}
