package model.players;

import model.game.logic.Game;
import model.game.logic.PlayerListener;
import model.units.*;
import model.units.snake.Direction;

public class EuristickBot extends PlayerListener {
    private final int range;

    public EuristickBot(Game game, Integer snakeId) {
        this(game, snakeId, 3);
    }

    public EuristickBot(Game game, Integer snakeId, int range) {

        super(game, snakeId);
        this.range = range;
    }

    @Override
    public Direction nextDirection() {
        return null;
    }

    private double getPenalty(GameUnit gameUnit) {
        if (gameUnit == null) {
            return 100;
        }
        if (gameUnit instanceof Wall) {
            return 100;
        }
        if (gameUnit instanceof Empty) {
            return -5;
        }
        if (gameUnit instanceof Food food) {
            return -((double) food.getValue() + 1) / 2 * 50;
        }
        if (gameUnit instanceof SnakeBody snakeBody) {
            double res = 50;
            if (snakeBody.getSnake().getBody().getLast() == snakeBody) {
                res = 10;
            }
            if (snakeBody.getSnake() == game.getSnakeMap().get(mySnakeId)) {
                if (snakeBody.isHead()) {
                    res = 0;
                }
            } else {
                res += 40;
            }
            return res;
        }
        return 0;
    }

    private double distanceScale(double distance) {
        return 1 / (distance * distance + distance + 0.2);
    }
}
