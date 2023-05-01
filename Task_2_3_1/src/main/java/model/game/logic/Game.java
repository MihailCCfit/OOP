package model.game.logic;

import model.game.field.GameField;
import model.units.Empty;
import model.units.GameUnit;
import model.units.Snake;
import model.units.SnakeBody;
import model.units.snake.Direction;

import java.util.HashMap;
import java.util.Map;

public class Game {
    private GameField field;
    private Map<Integer, Snake> snakeMap;
    private final GameLogic gameLogic;
    private int tick = 0;

    private GameUnit fieldGet(int x, int y) {
        return field.get(x, y);
    }

    public Game(GameField field, Map<Integer, Snake> snakes) {
        this.field = field;
        this.snakeMap = snakes;
        snakes.values().forEach((snake -> {
            field.set(snake.getHead());
        }));
        gameLogic = new GameLogic(this);
    }

    public void tick() {
        if (tick % 10 == 0) {
            snakeMap.values().forEach(this::moveSnake);
        }

        if (tick % 50 == 0) {
            gameLogic.spawnFood();
        }
        tick++;
        if (tick >= 100) {
            tick = 0;
        }
    }

    private void moveSnake(Snake snake) {
        if (!snake.isControllable()) {
            return;
        }
        if (!snake.getBody().isEmpty()) {
            SnakeBody body = snake.getBody().getLast();
            field.set(new Empty(body.getX(), body.getY()));
        } else {
            field.set(new Empty(snake.getHead().getX(), snake.getHead().getY()));
        }
        gameLogic.moveSnake(snake);
        if (snake.isControllable()) {
            snake.getBody().forEach(this::setGameUnit);
            setGameUnit(snake.getHead());
        }

    }

    public void setGameUnit(GameUnit unit) {
        field.set(unit);
    }

    public GameUnit getUnitAt(int x, int y) {
        return field.get(x, y);
    }

    public GameField getField() {
        return field;
    }

    public Map<Integer, Snake> getSnakeMap() {
        return snakeMap;
    }

    public int width() {
        return field.width();
    }

    public int height() {
        return field.height();
    }

    public Game getCopy() {
        GameField gameField = new GameField(field.width(), field.height());
        field.getAll().forEach((gameUnit) -> {
            gameField.set(gameUnit.getCopy());
        });
        Map<Integer, Snake> newSnakeMap = new HashMap<>();
        snakeMap.forEach((id, snake) -> {
            Snake newSnake = new Snake((SnakeBody) gameField.get(snake.getHead().getX(), snake.getHead().getY()));
            newSnakeMap.put(id, newSnake);
        });
        Game newGame = new Game(gameField, newSnakeMap);

        return newGame;
    }

    public void changeDirection(Integer snakeID, Direction direction) {
        snakeMap.get(snakeID).changeDirection(direction);
    }

}
