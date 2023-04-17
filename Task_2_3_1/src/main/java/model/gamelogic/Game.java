package model.gamelogic;

import model.gameField.GameField;
import model.units.GameUnit;
import model.units.Snake;
import model.units.SnakeBody;

import java.util.List;

public class Game {
    private GameField field;
    private List<Snake> snakeList;

    private GameUnit fieldGet(int x, int y) {
        return field.get(x, y);
    }

    public Game(GameField field, List<Snake> snakes) {
        this.field = field;
        this.snakeList = snakes;
    }

    public void tick() {

    }

    public void moveSnake(Snake snake) {
        SnakeBody head = snake.getHead();
        snake.getBody().add(new SnakeBody(head.getX(), head.getY(), head.getDirection()));
        snake.getBody().removeLast();
        head.move();
    }

    public void setGameUnit(GameUnit unit) {
        field.set(unit);
    }

    public GameUnit getUnitAt(int x, int y) {
        return field.get(x, y);
    }


}
