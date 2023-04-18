package model.gamelogic;

import model.gameField.GameField;
import model.units.GameUnit;
import model.units.Snake;

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

//        head.move();
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

    public List<Snake> getSnakeList() {
        return snakeList;
    }

    public int width() {
        return field.width();
    }

    public int height() {
        return field.height();
    }
}
