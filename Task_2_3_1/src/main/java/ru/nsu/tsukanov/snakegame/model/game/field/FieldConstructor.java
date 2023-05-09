package ru.nsu.tsukanov.snakegame.model.game.field;

import ru.nsu.tsukanov.snakegame.model.game.logic.Game;
import ru.nsu.tsukanov.snakegame.model.units.Empty;
import ru.nsu.tsukanov.snakegame.model.units.GameUnit;
import ru.nsu.tsukanov.snakegame.model.units.Snake;
import ru.nsu.tsukanov.snakegame.model.units.SnakeBody;

import java.util.*;

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
        if (unit instanceof SnakeBody) {
            snakeHeads.add((SnakeBody) unit);
        }
        gameField.set(unit);
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

    public List<GameUnit> getAll() {
        return gameField.getAll();
    }

    public List<SnakeBody> getSnakes() {
        return snakeHeads.stream().toList();
    }

    public int width() {
        return gameField.width();
    }

    public int height() {
        return gameField.height();
    }
}
