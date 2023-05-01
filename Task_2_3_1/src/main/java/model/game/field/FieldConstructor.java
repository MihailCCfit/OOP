package model.game.field;

import model.game.logic.Game;
import model.units.Empty;
import model.units.GameUnit;
import model.units.Snake;
import model.units.SnakeBody;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FieldConstructor {
    private final GameField gameField;
    private final Set<SnakeBody> snakeHeads = new HashSet<>();

    public FieldConstructor(int width, int height) {
        gameField = new GameField(width, height);
    }

    public void setUnit(GameUnit unit) {
        int x = unit.getX();
        int y = unit.getY();
        if (x > gameField.width() || y > gameField.height() || x < 0 || y < 0) {
            throw new RuntimeException();
        }
        if (gameField.get(x, y) instanceof SnakeBody) {
            snakeHeads.remove(gameField.get(x, y));
        }
        if (gameField.get(x, y) instanceof Empty) {
            gameField.set(unit);
        }
        if (unit instanceof SnakeBody) {
            snakeHeads.add((SnakeBody) unit);
        }
    }

    public void removeUnit(int x, int y) {
        gameField.set(new Empty(x, y));
    }

    public GameUnit getUnit(int x, int y) {
        return gameField.get(x, y);
    }

    public Game getGameField() {
        Map<Integer, Snake> snakes = new HashMap<>();
        int i = 0;
        for (SnakeBody snakeBody : snakeHeads) {
            snakes.put(i, new Snake(snakeBody));
            i++;
        }
        return new Game(gameField, snakes);
    }
}
